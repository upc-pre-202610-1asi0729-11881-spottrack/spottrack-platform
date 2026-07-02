package com.spottrack.platform.iam.application.acl;

import com.spottrack.platform.iam.application.commandservices.PendingRegistrationCommandService;
import com.spottrack.platform.iam.application.commandservices.UserCommandService;
import com.spottrack.platform.iam.application.queryservices.UserQueryService;
import com.spottrack.platform.iam.domain.model.aggregates.PendingRegistration;
import com.spottrack.platform.iam.domain.model.commands.ProvisionIamAccountCommand;
import com.spottrack.platform.iam.domain.model.commands.SavePendingRegistrationCommand;
import com.spottrack.platform.iam.domain.model.entities.Role;
import com.spottrack.platform.iam.domain.model.queries.GetUserByUsernameQuery;
import com.spottrack.platform.iam.domain.model.valueobjects.Roles;
import com.spottrack.platform.iam.domain.repositories.PendingRegistrationRepository;
import com.spottrack.platform.iam.interfaces.acl.IamContextFacade;
import com.spottrack.platform.iam.interfaces.acl.dto.PendingRegistrationDto;
import com.spottrack.platform.iam.interfaces.acl.dto.ProvisionedAccountDto;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class IamContextFacadeImpl implements IamContextFacade {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final PendingRegistrationCommandService pendingRegistrationCommandService;
    private final PendingRegistrationRepository pendingRegistrationRepository;

    public IamContextFacadeImpl(
            UserQueryService userQueryService,
            UserCommandService userCommandService,
            PendingRegistrationCommandService pendingRegistrationCommandService,
            PendingRegistrationRepository pendingRegistrationRepository) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
        this.pendingRegistrationCommandService = pendingRegistrationCommandService;
        this.pendingRegistrationRepository = pendingRegistrationRepository;
    }

    @Override
    public Optional<Long> fetchUserIdByUsername(String username) {
        return userQueryService.handle(new GetUserByUsernameQuery(username))
                .map(user -> user.getId());
    }

    @Override
    public boolean existsUserByUsername(String username) {
        return userQueryService.handle(new GetUserByUsernameQuery(username)).isPresent();
    }

    @Override
    public Result<UUID, ApplicationError> savePendingRegistration(SavePendingRegistrationCommand command) {
        return pendingRegistrationCommandService.handle(command);
    }

    @Override
    public Optional<PendingRegistrationDto> findPendingRegistrationById(UUID registrationId) {
        return pendingRegistrationRepository.findById(registrationId)
                .map(p -> new PendingRegistrationDto(
                        p.getRegistrationId(),
                        p.getEmail(),
                        p.getMembershipTier(),
                        p.isExpired()
                ));
    }

    @Override
    public boolean existsPendingRegistrationByEmail(String email) {
        return pendingRegistrationRepository.existsActivePendingByEmail(email);
    }

    @Override
    @Transactional
    public Optional<ProvisionedAccountDto> consumePendingRegistration(UUID registrationId) {
        var pendingOpt = pendingRegistrationRepository.findById(registrationId);
        if (pendingOpt.isEmpty()) {
            log.warn("consumePendingRegistration: no pending registration found for id {}", registrationId);
            return Optional.empty();
        }

        var pending = pendingOpt.get();

        if (pending.getStatus().name().equals("CONSUMED")) {
            // Idempotent path: webhook already processed this registration.
            // Return the existing user data so downstream can verify their own idempotency.
            log.info("consumePendingRegistration: registration {} already consumed, returning existing user", registrationId);
            var existingUser = userQueryService.handle(new GetUserByUsernameQuery(pending.getEmail()));
            if (existingUser.isEmpty()) {
                log.error("consumePendingRegistration: registration {} is CONSUMED but no IAM user found for email {}", registrationId, pending.getEmail());
                return Optional.empty();
            }
            return Optional.of(toProvisionedAccountDto(existingUser.get().getId(), pending));
        }

        if (pending.isExpired()) {
            log.warn("consumePendingRegistration: registration {} has expired", registrationId);
            return Optional.empty();
        }

        // Safety net: if user already exists despite PENDING status (extreme race), mark consumed and return.
        var existingUser = userQueryService.handle(new GetUserByUsernameQuery(pending.getEmail()));
        if (existingUser.isPresent()) {
            log.warn("consumePendingRegistration: IAM user already exists for {} — marking pending {} as consumed", pending.getEmail(), registrationId);
            pending.consume();
            pendingRegistrationRepository.save(pending);
            return Optional.of(toProvisionedAccountDto(existingUser.get().getId(), pending));
        }

        var provisionCommand = new ProvisionIamAccountCommand(
                pending.getEmail(),
                pending.getHashedPassword(),
                List.of(new Role(Roles.ROLE_ADMIN))
        );

        var result = userCommandService.handle(provisionCommand);
        if (result instanceof Result.Failure<?, ?> failure) {
            log.error("consumePendingRegistration: failed to provision IAM account for registration {}: {}", registrationId, failure.error());
            return Optional.empty();
        }

        var newUser = ((Result.Success<com.spottrack.platform.iam.domain.model.aggregates.User, ?>) result).value();
        pending.consume();
        pendingRegistrationRepository.save(pending);

        log.info("consumePendingRegistration: provisioned IAM user {} for registration {}", newUser.getId(), registrationId);
        return Optional.of(toProvisionedAccountDto(newUser.getId(), pending));
    }

    private ProvisionedAccountDto toProvisionedAccountDto(Long userId, PendingRegistration pending) {
        return new ProvisionedAccountDto(
                userId,
                pending.getEmail(),
                pending.getFirstName(),
                pending.getLastName(),
                pending.getPhoneNumber(),
                pending.getDni(),
                pending.getCompanyName(),
                pending.getRuc(),
                pending.getLegalStructure(),
                pending.getCompanyPhone(),
                pending.getCompanyEmail(),
                pending.getStreetAddress(),
                pending.getCity(),
                pending.getDistrict(),
                pending.getMembershipTier()
        );
    }
}
