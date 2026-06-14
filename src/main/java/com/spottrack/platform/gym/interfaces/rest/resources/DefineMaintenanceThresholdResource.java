package com.spottrack.platform.gym.interfaces.rest.resources;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;

import java.time.LocalDate;

public record DefineMaintenanceThresholdResource(EquipmentId equipmentId, LocalDate threshold) {
}
