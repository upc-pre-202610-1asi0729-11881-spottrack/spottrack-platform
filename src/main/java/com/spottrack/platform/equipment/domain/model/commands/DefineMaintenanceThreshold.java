package com.spottrack.platform.equipment.domain.model.commands;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;

import java.time.LocalDate;

public record DefineMaintenanceThreshold(EquipmentId equipmentId, LocalDate threshold) {
    public DefineMaintenanceThreshold {
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
