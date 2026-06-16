package com.spottrack.platform.profiles.application.internal.eventhandlers;

import com.spottrack.platform.profiles.application.commandservices.AdminCommandService;
import com.spottrack.platform.profiles.application.commandservices.ClientCommandService;
import com.spottrack.platform.profiles.domain.model.commands.CreateAdminCommand;
import com.spottrack.platform.profiles.domain.model.commands.CreateClientCommand;
import com.spottrack.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.spottrack.platform.profiles.interfaces.events.RoleAssignedIntegrationEvent;
import com.spottrack.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleAssignedEventHandler {
    private final ClientCommandService clientCommandService;
    private final AdminCommandService adminCommandService;

    public RoleAssignedEventHandler(ClientCommandService clientCommandService, AdminCommandService adminCommandService) {
        this.clientCommandService = clientCommandService;
        this.adminCommandService = adminCommandService;
    }

    @EventListener
    public void on(RoleAssignedIntegrationEvent event) {
        var email = new EmailAddress(event.email());
        var firstName = (event.firstName() == null || event.firstName().isBlank()) ? "Pending" : event.firstName();
        var lastName = (event.lastName() == null || event.lastName().isBlank()) ? "Profile" : event.lastName();

        switch (event.role()) {
            case "ROLE_CLIENT" -> {
                var command = new CreateClientCommand(event.userId(), email, firstName, lastName, event.phoneNumber(), event.dni());
                var result = clientCommandService.handle(command);
                if (result instanceof Result.Failure<?, ?> f) {
                    log.warn("Failed to create client for user {}: {}", event.userId(), f.error());
                }
            }
            case "ROLE_ADMIN" -> {
                var command = new CreateAdminCommand(event.userId(), email, firstName, lastName, event.phoneNumber(), event.dni());
                var result = adminCommandService.handle(command);
                if (result instanceof Result.Failure<?, ?> f) {
                    log.warn("Failed to create admin for user {}: {}", event.userId(), f.error());
                }
            }
            default -> log.warn("Unrecognised role '{}' for user {}, no profile created", event.role(), event.userId());
        }
    }
}
