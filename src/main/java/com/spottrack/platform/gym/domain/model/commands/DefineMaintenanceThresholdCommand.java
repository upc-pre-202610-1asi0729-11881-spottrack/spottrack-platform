package com.spottrack.platform.gym.domain.model.commands;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;

import java.time.LocalDate;

public record DefineMaintenanceThresholdCommand(EquipmentId equipmentId, LocalDate threshold) {
    public DefineMaintenanceThresholdCommand {
        if (equipmentId == null) {
            throw new IllegalArgumentException("equipment.command.defineMaintenanceThreshold.equipmentId.notNull");
        }
        if (threshold == null) {
            throw new IllegalArgumentException("equipment.command.defineMaintenanceThreshold.threshold.notNull");
        }
        if (threshold.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("equipment.command.defineMaintenanceThreshold.threshold.pastDate");
        }
    }
}
