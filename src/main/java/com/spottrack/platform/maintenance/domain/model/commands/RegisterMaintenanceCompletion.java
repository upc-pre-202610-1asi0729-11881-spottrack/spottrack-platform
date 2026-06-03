package com.spottrack.platform.maintenance.domain.model.commands;

import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;

public record RegisterMaintenanceCompletion(
        TechnicalTicketId ticketId,
        MaintenanceId maintenanceId,
        String notes
) {
    public RegisterMaintenanceCompletion {
        if (ticketId == null)
            throw new IllegalArgumentException("maintenance.command.registerCompletion.ticketId.notNull");
        if (maintenanceId == null)
            throw new IllegalArgumentException("maintenance.command.registerCompletion.maintenanceId.notNull");
        if (notes == null || notes.isBlank())
            throw new IllegalArgumentException("maintenance.command.registerCompletion.notes.notBlank");
    }
}
