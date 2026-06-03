package com.spottrack.platform.maintenance.domain.model.commands;

import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;

public record UpdateMaintenanceStatus(
        TechnicalTicketId ticketId,
        MaintenanceStatus newStatus
) {
    public UpdateMaintenanceStatus {
        if (ticketId == null)
            throw new IllegalArgumentException("maintenance.command.updateMaintenanceStatus.ticketId.notNull");
        if (newStatus == null)
            throw new IllegalArgumentException("maintenance.command.updateMaintenanceStatus.newStatus.notNull");
    }
}
