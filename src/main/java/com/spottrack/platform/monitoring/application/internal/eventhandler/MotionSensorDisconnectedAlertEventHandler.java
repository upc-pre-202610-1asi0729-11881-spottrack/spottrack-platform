package com.spottrack.platform.monitoring.application.internal.eventhandler;

import com.spottrack.platform.gym.interfaces.acl.GymContextFacade;
import com.spottrack.platform.iam.interfaces.acl.IamContextFacade;
import com.spottrack.platform.monitoring.interfaces.events.MotionSensorDisconnectedIntegrationEvent;
import com.spottrack.platform.shared.application.commandservices.AlertCommandService;
import com.spottrack.platform.shared.domain.model.commands.CreateAlertCommand;
import com.spottrack.platform.shared.domain.model.valueobjects.AlertSeverity;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Consumes {@link MotionSensorDisconnectedIntegrationEvent} to create an Alert
 * for the admin who owns the affected equipment.
 */
@Service
public class MotionSensorDisconnectedAlertEventHandler {

    private final GymContextFacade gymContextFacade;
    private final IamContextFacade iamContextFacade;
    private final AlertCommandService alertCommandService;

    public MotionSensorDisconnectedAlertEventHandler(GymContextFacade gymContextFacade,
                                                       IamContextFacade iamContextFacade,
                                                       AlertCommandService alertCommandService) {
        this.gymContextFacade = gymContextFacade;
        this.iamContextFacade = iamContextFacade;
        this.alertCommandService = alertCommandService;
    }

    @EventListener
    public void on(MotionSensorDisconnectedIntegrationEvent event) {
        gymContextFacade.resolveGymIdForEquipment(event.equipmentId())
                .map(gymContextFacade::fetchAdminUserIdByGymId)
                .filter(adminUserId -> adminUserId != 0L)
                .filter(adminUserId -> iamContextFacade.shouldNotify(adminUserId, AlertSeverity.WARNING))
                .ifPresent(adminUserId -> alertCommandService.handle(new CreateAlertCommand(
                        adminUserId,
                        event.equipmentId(),
                        AlertSeverity.WARNING,
                        "IoT motion sensor disconnected from the network."
                )));
    }
}
