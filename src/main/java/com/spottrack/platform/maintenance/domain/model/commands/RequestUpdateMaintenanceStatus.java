package com.spottrack.platform.maintenance.domain.model.commands;

import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;

public record RequestUpdateMaintenanceStatus(
        TechnicalTicketId ticketId,
        MaintenanceStatus newStatus
) {
    public RequestUpdateMaintenanceStatus {
        if (ticketId == null)
            throw new IllegalArgumentException("maintenance.command.requestUpdateStatus.ticketId.notNull");
        if (newStatus == null)
            throw new IllegalArgumentException("maintenance.command.requestUpdateStatus.newStatus.notNull");
    }
}
