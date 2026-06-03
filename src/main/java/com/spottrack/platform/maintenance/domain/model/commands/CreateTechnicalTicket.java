package com.spottrack.platform.maintenance.domain.model.commands;

import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceId;

public record CreateTechnicalTicket(
        MaintenanceId maintenanceId,
        String equipmentId,
        String description
) {
    public CreateTechnicalTicket {
        if (maintenanceId == null)
            throw new IllegalArgumentException("maintenance.command.createTechnicalTicket.maintenanceId.notNull");
        if (equipmentId == null || equipmentId.isBlank())
            throw new IllegalArgumentException("maintenance.command.createTechnicalTicket.equipmentId.notBlank");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("maintenance.command.createTechnicalTicket.description.notBlank");
    }
}
