package com.spottrack.platform.equipment.domain.model.commands;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.ZoneId;

import java.security.PrivilegedActionException;

public record RelocateEquipment(EquipmentId equipmentId, ZoneId zoneId) {
    public RelocateEquipment{
        if (equipmentId == null){
            throw new IllegalArgumentException("equipmentId must not be null");
        }
        if (zoneId == null){
            throw new IllegalArgumentException("zoneId must not be null");
        }
    }
}
