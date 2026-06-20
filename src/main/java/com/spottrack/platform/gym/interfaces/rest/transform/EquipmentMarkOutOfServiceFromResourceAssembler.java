package com.spottrack.platform.gym.interfaces.rest.transform;

import com.spottrack.platform.gym.domain.model.commands.MarkEquipmentOutOfService;
import com.spottrack.platform.gym.interfaces.rest.resources.MarkEquipmentOutOfServiceResource;

public class EquipmentMarkOutOfServiceFromResourceAssembler {
    public static MarkEquipmentOutOfService toCommandFromResource(MarkEquipmentOutOfServiceResource resource){
        return new MarkEquipmentOutOfService(resource.equipmentId());
    }
}
