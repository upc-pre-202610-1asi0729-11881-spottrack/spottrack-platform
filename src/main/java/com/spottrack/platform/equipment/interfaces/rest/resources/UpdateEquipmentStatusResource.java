package com.spottrack.platform.equipment.interfaces.rest.resources;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;

public record UpdateEquipmentStatusResource(String id, EquipmentStatus status) {

}
