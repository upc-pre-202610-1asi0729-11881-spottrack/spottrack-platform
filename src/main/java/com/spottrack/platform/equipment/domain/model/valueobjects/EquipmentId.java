package com.spottrack.platform.equipment.domain.model.valueobjects;

public record EquipmentId(String id) {
    public EquipmentId {
        if (id == null){
            throw new IllegalArgumentException("template-message");
        }
    }
}
