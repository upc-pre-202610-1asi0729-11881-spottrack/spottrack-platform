package com.spottrack.platform.maintenance.application.internal.eventhandlers;

import com.spottrack.platform.maintenance.application.commandServices.MaintenanceCommandService;
import com.spottrack.platform.maintenance.domain.model.commands.CreateTechnicalTicketForMaintenance;
import com.spottrack.platform.maintenance.domain.model.events.MaintenanceRequestedEvent;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketPriority;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Policy: [Maintenance Requested] Create Technical Ticket
 *
 * Previously RequestMaintenance created a Maintenance row with no further effect —
 * nothing opened a ticket or marked the equipment out of service. This closes that
 * gap by opening a ticket for the same Maintenance right away, which in turn lets
 * TechnicalTicketCreatedEventHandler mark the equipment out of service as intended.
 *
 * Defaults to MEDIUM/CORRECTIVE since RequestMaintenance doesn't collect priority/type
 * (an admin can still re-prioritize or reclassify via ModifyTicketStatus afterward).
 */
@Component
public class MaintenanceRequestedEventHandler {

    private final MaintenanceCommandService maintenanceCommandService;

    public MaintenanceRequestedEventHandler(MaintenanceCommandService maintenanceCommandService) {
        this.maintenanceCommandService = maintenanceCommandService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(MaintenanceRequestedEvent event) {
        maintenanceCommandService.handle(new CreateTechnicalTicketForMaintenance(
                event.maintenanceId(), TicketPriority.MEDIUM, TicketType.CORRECTIVE));
    }
}
