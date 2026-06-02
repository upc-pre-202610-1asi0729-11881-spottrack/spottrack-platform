package com.spottrack.platform.equipment.domain.model.commands;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;

public record UpdateEquipmentStatus(EquipmentId id, EquipmentStatus status) {
    public UpdateEquipmentStatus{
        if (id == null){
            throw new IllegalArgumentException();
        }
        if (status == null){
            throw new IllegalArgumentException();
        }
    }
}
