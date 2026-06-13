package com.spottrack.platform.equipment.interfaces.rest.transform;

import com.spottrack.platform.equipment.domain.model.commands.MarkEquipmentOutOfService;
import com.spottrack.platform.equipment.interfaces.rest.resources.MarkEquipmentOutOfServiceResource;

public class EquipmentMarkOutOfServiceFromResourceAssembler {
    public static MarkEquipmentOutOfService toCommandFromResource(MarkEquipmentOutOfServiceResource resource){
        return new MarkEquipmentOutOfService(resource.equipmentId());
    }
}
