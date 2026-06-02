package com.spottrack.platform.equipment.interfaces.rest.transform;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.interfaces.rest.resources.EquipmentResource;

public class EquipmentResourceFromEntityAssembler {
    public static EquipmentResource toResourceFromEntity(Equipment equipment){
        return new EquipmentResource(
                equipment.getEquipmentName(),
                equipment.getStatus(),
                equipment.getModel(),
                equipment.getManufacturer().getName(),
                equipment.getManufacturer().getCountry(),
                equipment.getManufacturer().getWebsite(),
                equipment.getPurchasePrice().currency(),
                equipment.getPurchasePrice().amount()

        );
    }
}
