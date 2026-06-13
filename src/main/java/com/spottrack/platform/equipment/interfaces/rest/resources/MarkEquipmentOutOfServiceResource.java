package com.spottrack.platform.equipment.interfaces.rest.resources;

import com.spottrack.platform.equipment.domain.model.commands.MarkEquipmentOutOfService;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;

public record MarkEquipmentOutOfServiceResource(
        String equipmentId,
        EquipmentStatus equipmentStatus
) {

}
