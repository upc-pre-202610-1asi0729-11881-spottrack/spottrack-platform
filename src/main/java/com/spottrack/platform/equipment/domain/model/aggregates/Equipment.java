package com.spottrack.platform.equipment.domain.model.aggregates;

import com.spottrack.platform.equipment.domain.model.entities.Manufacturer;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.util.UUID;


@Entity
public class Equipment {
    @EmbeddedId
    private EquipmentId id;
    private EquipmentStatus status;
    private String equipmentName;
    private String model;
    private Manufacturer manufacturer;

    Equipment(EquipmentStatus status, String equipmentName, String model, Manufacturer manufacturer){
        this.id = new EquipmentId(UUID.randomUUID().toString());
        this.equipmentName = equipmentName;
        this.model = model;
        this.manufacturer = manufacturer;

    }
}