package com.spottrack.platform.equipment.domain.model.commands;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;

public record MarkEquipmentOutOfService(
        EquipmentId id,
        EquipmentStatus status
) {
    public MarkEquipmentOutOfService{
        if (id == null){
            throw new IllegalArgumentException("");
        }

        if (status == null){
            throw new IllegalArgumentException("");
        }
    }
}
