package com.spottrack.platform.maintenance.domain.model.commands;

public record DecommissionEquipment(String equipmentId) {

    public DecommissionEquipment {
        if (equipmentId == null || equipmentId.isBlank())
            throw new IllegalArgumentException("maintenance.command.decommissionEquipment.equipmentId.notBlank");
    }
}
