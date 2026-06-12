package com.spottrack.platform.equipment.domain.model.queries;

public record GetEquipmentsByName(String equipmentName) {
    public GetEquipmentsByName{
        if (equipmentName == null || equipmentName.isBlank()) {
            throw new IllegalArgumentException("name must not be null or blank");
        }
    }
}
