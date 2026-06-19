package com.spottrack.platform.maintenance.application.internal.eventhandlers;

import com.spottrack.platform.maintenance.application.commandServices.MaintenanceCommandService;
import com.spottrack.platform.maintenance.domain.model.commands.UpdateMaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.events.TicketStatusModifiedEvent;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TicketStatusModifiedEventHandler {

    private final MaintenanceCommandService maintenanceCommandService;

    public TicketStatusModifiedEventHandler(MaintenanceCommandService maintenanceCommandService) {
        this.maintenanceCommandService = maintenanceCommandService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void on(TicketStatusModifiedEvent event) {
        if (TicketStatus.RESOLVED.name().equals(event.newStatus())) {
            maintenanceCommandService.handle(new UpdateMaintenanceStatus(
                    new TechnicalTicketId(event.ticketId()),
                    MaintenanceStatus.COMPLETED
            ));
        }
    }
}
