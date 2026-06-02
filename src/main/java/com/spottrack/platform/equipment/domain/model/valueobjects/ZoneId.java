package com.spottrack.platform.equipment.domain.model.valueobjects;

public record ZoneId(String uuid) {
    private static final String NOT_BLANK= "manufacturer.error.manufacturerId.notBlank";
    private static final String ID_ZERO = "id.error.invalid";
    public ZoneId{
        if (uuid == null || uuid.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK);
        }

        if (uuid.isBlank()){
            throw new IllegalArgumentException(ID_ZERO);
        }
    }
}
