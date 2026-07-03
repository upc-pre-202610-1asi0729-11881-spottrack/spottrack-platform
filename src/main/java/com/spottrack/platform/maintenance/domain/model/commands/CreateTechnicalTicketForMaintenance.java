package com.spottrack.platform.maintenance.domain.model.commands;

import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketPriority;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketType;

/**
 * Creates a TechnicalTicket for a Maintenance that already exists, reusing its
 * equipmentId/description instead of spawning a duplicate Maintenance row.
 * Used by MaintenanceRequestedEventHandler to auto-open a ticket right after
 * RequestMaintenance, as opposed to CreateTechnicalTicketCommand which is the
 * standalone entry point that creates its own backing Maintenance.
 */
public record CreateTechnicalTicketForMaintenance(
        String maintenanceId,
        TicketPriority priority,
        TicketType type
) {
    public CreateTechnicalTicketForMaintenance {
        if (maintenanceId == null || maintenanceId.isBlank())
            throw new IllegalArgumentException("maintenance.command.createTechnicalTicketForMaintenance.maintenanceId.notBlank");
        if (priority == null)
            throw new IllegalArgumentException("maintenance.command.createTechnicalTicketForMaintenance.priority.notNull");
        if (type == null)
            throw new IllegalArgumentException("maintenance.command.createTechnicalTicketForMaintenance.type.notNull");
    }
}
