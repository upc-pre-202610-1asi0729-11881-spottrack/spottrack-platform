package com.spottrack.platform.maintenance.domain.model.commands;
public record RequestMaintenance(
        String equipmentId,
        String requestedBy,
        String description
) {
    public RequestMaintenance {
        if (equipmentId == null || equipmentId.isBlank())
            throw new IllegalArgumentException("maintenance.command.requestMaintenance.equipmentId.notBlank");
        if (requestedBy == null || requestedBy.isBlank())
            throw new IllegalArgumentException("maintenance.command.requestMaintenance.requestedBy.notBlank");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("maintenance.command.requestMaintenance.description.notBlank");
    }
}
