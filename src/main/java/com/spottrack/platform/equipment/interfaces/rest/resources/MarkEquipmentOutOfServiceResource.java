package com.spottrack.platform.equipment.interfaces.rest.resources;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;

public record MarkEquipmentOutOfServiceResource(
        EquipmentId equipmentId
) {
}
