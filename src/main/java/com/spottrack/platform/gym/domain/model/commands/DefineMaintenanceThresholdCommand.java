package com.spottrack.platform.gym.domain.model.commands;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;

import java.util.Date;

public record DefineMaintenanceThresholdCommand(EquipmentId equipmentId, Date thresholdDate) {
}
