package com.spottrack.platform.gym.domain.model.commands;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;

public record MarkMaintenanceThresholdReached(EquipmentId equipmentId) {
    public MarkMaintenanceThresholdReached {
        if (equipmentId == null)
            throw new IllegalArgumentException("equipment.command.markMaintenanceThresholdReached.equipmentId.notNull");
    }
}
