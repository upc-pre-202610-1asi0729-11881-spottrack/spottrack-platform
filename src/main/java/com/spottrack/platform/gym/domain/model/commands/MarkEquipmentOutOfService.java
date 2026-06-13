package com.spottrack.platform.gym.domain.model.commands;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;

/**
 *
 * @param id
 *
 * Its intended that later, the aggregate modifies the status of the equipment
 */
public record MarkEquipmentOutOfService(
        EquipmentId id
) {
    public MarkEquipmentOutOfService{
        if (id == null){
            throw new IllegalArgumentException("");
        }


    }
}
