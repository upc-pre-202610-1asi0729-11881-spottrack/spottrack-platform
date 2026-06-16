package com.spottrack.platform.iam.application.internal.commandservices;

import com.spottrack.platform.iam.application.commandservices.UserCommandService;
import com.spottrack.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.spottrack.platform.iam.domain.model.aggregates.User;
import com.spottrack.platform.iam.domain.model.commands.SignUpCommand;
import com.spottrack.platform.iam.domain.model.entities.Role;
import com.spottrack.platform.iam.domain.repositories.RoleRepository;
import com.spottrack.platform.iam.domain.repositories.UserRepository;
import com.spottrack.platform.profiles.interfaces.events.RoleAssignedIntegrationEvent;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            HashingService hashingService,
            RoleRepository roleRepository,
            ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
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
}
