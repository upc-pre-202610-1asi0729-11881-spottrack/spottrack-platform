package com.spottrack.platform.gym.application.internal.eventhandlers;

import com.spottrack.platform.gym.domain.model.events.MaintenanceThresholdReachedEvent;
import com.spottrack.platform.gym.interfaces.acl.GymContextFacade;
import com.spottrack.platform.gym.interfaces.events.MaintenanceThresholdReachedIntegrationEvent;
import com.spottrack.platform.shared.application.commandservices.AlertCommandService;
import com.spottrack.platform.shared.domain.model.commands.CreateAlertCommand;
import com.spottrack.platform.shared.domain.model.valueobjects.AlertSeverity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Policy: [Threshold reached?] Emit Maintenance Request + create an Alert
 * for the owning admin (per the gym event-storming board).
 */
@Component
public class EquipmentMaintenanceThresholdReachedEventHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final GymContextFacade gymContextFacade;
    private final AlertCommandService alertCommandService;

    public EquipmentMaintenanceThresholdReachedEventHandler(ApplicationEventPublisher eventPublisher,
                                                              GymContextFacade gymContextFacade,
                                                              AlertCommandService alertCommandService) {
        this.eventPublisher = eventPublisher;
        this.gymContextFacade = gymContextFacade;
        this.alertCommandService = alertCommandService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(MaintenanceThresholdReachedEvent event) {
        eventPublisher.publishEvent(
                new MaintenanceThresholdReachedIntegrationEvent(event.equipmentId(), event.threshold()));

        gymContextFacade.resolveGymIdForEquipment(event.equipmentId())
                .map(gymContextFacade::fetchAdminUserIdByGymId)
                .filter(adminUserId -> adminUserId != 0L)
                .ifPresent(adminUserId -> alertCommandService.handle(new CreateAlertCommand(
                        adminUserId,
                        event.equipmentId(),
                        AlertSeverity.WARNING,
                        "Equipment maintenance threshold reached: preventive maintenance required."
                )));
    }
}
