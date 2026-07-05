package com.spottrack.platform.gym.interfaces.rest.resources;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;

public record MarkEquipmentOutOfServiceResource(
        EquipmentId equipmentId
) {
}
