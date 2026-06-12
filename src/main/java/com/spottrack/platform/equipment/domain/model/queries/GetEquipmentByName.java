package com.spottrack.platform.equipment.domain.model.queries;

public record GetEquipmentByName(String equipmentName) {
    public GetEquipmentByName {
        if (equipmentName == null || equipmentName.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank");
        }
    }
}
