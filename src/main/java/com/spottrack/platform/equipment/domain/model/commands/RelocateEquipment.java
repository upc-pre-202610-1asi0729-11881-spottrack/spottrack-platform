package com.spottrack.platform.equipment.domain.model.commands;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.GymId;
import com.spottrack.platform.equipment.domain.model.valueobjects.ZoneId;

public record RelocateEquipment(EquipmentId equipmentId, GymId gymId, ZoneId zoneId) {
    public RelocateEquipment{
        if (equipmentId == null) throw new IllegalArgumentException("equipmentId must not be null");
        if (gymId == null) throw new IllegalArgumentException("gymId must not be null");
        if (zoneId == null) throw new IllegalArgumentException("zoneId must not be null");
    }
}
