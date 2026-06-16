package com.spottrack.platform.iam.application.internal.commandservices;

import com.spottrack.platform.iam.application.commandservices.UserCommandService;
import com.spottrack.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.spottrack.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.spottrack.platform.iam.domain.model.aggregates.User;
import com.spottrack.platform.iam.domain.model.commands.ResetPasswordCommand;
import com.spottrack.platform.iam.domain.model.commands.SignInCommand;
import com.spottrack.platform.iam.domain.model.commands.SignOutCommand;
import com.spottrack.platform.iam.domain.model.commands.SignUpCommand;
import com.spottrack.platform.iam.domain.model.entities.Role;
import com.spottrack.platform.iam.domain.repositories.RoleRepository;
import com.spottrack.platform.iam.domain.repositories.UserRepository;
import com.spottrack.platform.profiles.interfaces.events.RoleAssignedIntegrationEvent;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            HashingService hashingService,
            TokenService tokenService,
            RoleRepository roleRepository,
            ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<User, ApplicationError> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username())) {
            return Result.failure(ApplicationError.conflict("USER", "Username already exists: " + command.username()));
        }

        List<Role> roles = Role.validateRoleSet(command.roles()).stream()
                .map(role -> roleRepository.findByName(role.getName()).orElse(role))
                .toList();

        String encodedPassword = hashingService.encode(command.password());
        User user = new User(command.username(), encodedPassword, roles);
        User savedUser = userRepository.save(user);

        User reloadedUser = userRepository.findById(savedUser.getId()).orElse(savedUser);

        String firstRoleName = reloadedUser.getRoles().stream()
                .findFirst()
                .map(Role::getStringName)
                .orElse("");

        eventPublisher.publishEvent(new RoleAssignedIntegrationEvent(
                reloadedUser.getId(),
                reloadedUser.getUsername(),
                firstRoleName,
                "",
                "",
                "",
                ""
        ));

        return Result.success(reloadedUser);
    }

    @Override
    public Result<ImmutablePair<User, String>, ApplicationError> handle(SignInCommand command) {
        var userOptional = userRepository.findByUsername(command.username());
        if (userOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("USER", command.username()));
        }
        var user = userOptional.get();
        if (!hashingService.matches(command.password(), user.getPassword())) {
            return Result.failure(ApplicationError.validationError("credentials", "Invalid credentials"));
        }
        String token = tokenService.generateToken(user.getUsername());
        return Result.success(ImmutablePair.of(user, token));
    }

    @Override
    public Result<User, ApplicationError> handle(ResetPasswordCommand command) {
        var userOptional = userRepository.findByUsername(command.username());
        if (userOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("USER", command.username()));
        }
        var user = userOptional.get();
        user.setPassword(hashingService.encode(command.newPassword()));
        var savedUser = userRepository.save(user);
        return Result.success(savedUser);
    }

    @Override
    public Result<User, ApplicationError> handle(SignOutCommand command) {
        var userOptional = userRepository.findByUsername(command.username());
        if (userOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("USER", command.username()));
        }
        return Result.success(userOptional.get());
    }
}
