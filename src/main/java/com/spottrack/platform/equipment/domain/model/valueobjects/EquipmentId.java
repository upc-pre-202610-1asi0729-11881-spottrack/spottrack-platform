package com.spottrack.platform.equipment.domain.model.valueobjects;

public record EquipmentId(String id) {
    private static final String NOT_BLANK= "equipment.error.equipmentId.notBlank";
    private static final String SIZE_LIMIT_EXCEEDED= "equipment.error.equipmentId.size";
    public EquipmentId {
        if (id == null || id.isBlank()){
            throw new IllegalArgumentException(NOT_BLANK);
        }

        if (id.length() > 256){
            throw new IllegalArgumentException(SIZE_LIMIT_EXCEEDED);
        }


    }
}
