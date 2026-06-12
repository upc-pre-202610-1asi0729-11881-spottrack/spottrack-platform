package com.spottrack.platform.equipment.domain.model.queries;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;

public record GetEquipmentsById(EquipmentId id) {
    public GetEquipmentsById {
        if (id == null){
            throw new IllegalArgumentException("id must not be null");
        }
    }
}
