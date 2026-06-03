package com.spottrack.platform.maintenance.domain.model.commands;

import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;

public record CompleteMaintenance(TechnicalTicketId ticketId) {

    public CompleteMaintenance {
        if (ticketId == null)
            throw new IllegalArgumentException("maintenance.command.completeMaintenance.ticketId.notNull");
    }
}
