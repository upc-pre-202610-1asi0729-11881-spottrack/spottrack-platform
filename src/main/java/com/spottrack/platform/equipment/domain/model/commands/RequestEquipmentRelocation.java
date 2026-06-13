package com.spottrack.platform.equipment.domain.model.commands;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.ZoneId;

public record RequestRelocateEquipment(EquipmentId equipmentId, ZoneId zoneId) {
    public RequestRelocateEquipment{
        if (equipmentId == null){
            throw new IllegalArgumentException("equipmentId must not be null");
        }
        if (zoneId == null){
            throw new IllegalArgumentException("zoneId must not be null");
        }
    }
}
