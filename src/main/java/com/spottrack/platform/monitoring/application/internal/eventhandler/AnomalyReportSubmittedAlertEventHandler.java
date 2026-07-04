package com.spottrack.platform.monitoring.application.internal.eventhandler;

import com.spottrack.platform.gym.interfaces.acl.GymContextFacade;
import com.spottrack.platform.monitoring.interfaces.events.AnomalyReportSubmittedIntegrationEvent;
import com.spottrack.platform.shared.application.commandservices.AlertCommandService;
import com.spottrack.platform.shared.domain.model.commands.CreateAlertCommand;
import com.spottrack.platform.shared.domain.model.valueobjects.AlertSeverity;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Consumes {@link AnomalyReportSubmittedIntegrationEvent} to create an Alert
 * for the admin who owns the affected equipment.
 */
@Service
public class AnomalyReportSubmittedAlertEventHandler {

    private final GymContextFacade gymContextFacade;
    private final AlertCommandService alertCommandService;

    public AnomalyReportSubmittedAlertEventHandler(GymContextFacade gymContextFacade,
                                                    AlertCommandService alertCommandService) {
        this.gymContextFacade = gymContextFacade;
        this.alertCommandService = alertCommandService;
    }

    @EventListener
    public void on(AnomalyReportSubmittedIntegrationEvent event) {
        gymContextFacade.resolveGymIdForEquipment(event.equipmentId())
                .map(gymContextFacade::fetchAdminUserIdByGymId)
                .filter(adminUserId -> adminUserId != 0L)
                .ifPresent(adminUserId -> alertCommandService.handle(new CreateAlertCommand(
                        adminUserId,
                        event.equipmentId(),
                        AlertSeverity.CRITICAL,
                        "Anomaly reported: " + event.anomalyDescription()
                )));
    }
}
