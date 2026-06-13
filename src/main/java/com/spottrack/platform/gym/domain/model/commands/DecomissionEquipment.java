package com.spottrack.platform.gym.domain.model.commands;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;

/**
 * Changes the state of an equipment to decommissioned
 */

public record DecomissionEquipment(
        EquipmentId equipmentId, EquipmentStatus equipmentStatus
) {
    public DecomissionEquipment {
        if (equipmentId == null){
            throw new IllegalArgumentException("equipmentId must not be null");
        }

        if (equipmentStatus == null){
            throw new IllegalArgumentException("equipmentStatus must not be null");

        }
    }

}
