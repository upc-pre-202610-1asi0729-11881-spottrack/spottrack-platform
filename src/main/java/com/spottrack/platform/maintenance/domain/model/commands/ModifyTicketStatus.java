package com.spottrack.platform.maintenance.domain.model.commands;

import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketStatus;

public record ModifyTicketStatus(
        TechnicalTicketId ticketId,
        TicketStatus newStatus
) {
    public ModifyTicketStatus {
        if (ticketId == null)
            throw new IllegalArgumentException("maintenance.command.modifyTicketStatus.ticketId.notNull");
        if (newStatus == null)
            throw new IllegalArgumentException("maintenance.command.modifyTicketStatus.newStatus.notNull");
    }
}
