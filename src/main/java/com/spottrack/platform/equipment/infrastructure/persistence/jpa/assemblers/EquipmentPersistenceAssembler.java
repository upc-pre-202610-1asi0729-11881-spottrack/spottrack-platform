package com.spottrack.platform.equipment.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.ManufacturerId;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities.EquipmentPersistenceEntity;

public class EquipmentPersistenceAssembler {
    private  EquipmentPersistenceAssembler(){

    }

    public static Equipment toDomainFromPersistence(EquipmentPersistenceEntity entity){
        var equipment = new Equipment();
        var equipmentId = new EquipmentId(entity.getEquipmentId());
        equipment.setId(equipmentId);
        equipment.setEquipmentName(entity.getEquipmentName());
        equipment.setModel(entity.getModel());
        equipment.setStatus(entity.getStatus());
        equipment.setManufacturerId(entity.getManufacturerId());
        equipment.setPurchasePrice(entity.getPurchasePrice());
        equipment.setMaintenanceThreshold(entity.getMaintenanceThreshold());
        return equipment;

    }


    public static EquipmentPersistenceEntity toPersistenceFromDomain (Equipment domain){
        var equipment = new EquipmentPersistenceEntity();
        equipment.setEquipmentId(domain.getId().uuid());
        equipment.setEquipmentName(domain.getEquipmentName());
        equipment.setStatus(domain.getStatus());
        equipment.setModel(domain.getModel());
        equipment.setManufacturerId(domain.getManufacturerId());
        equipment.setPurchasePrice(domain.getPurchasePrice());
        equipment.setMaintenanceThreshold(domain.getMaintenanceThreshold());

        return equipment;
    }

}
