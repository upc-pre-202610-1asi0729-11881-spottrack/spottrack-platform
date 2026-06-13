package com.spottrack.platform.equipment.interfaces.rest.transform;

import com.spottrack.platform.equipment.domain.model.commands.UpdateEquipmentStatus;
import com.spottrack.platform.equipment.interfaces.rest.resources.UpdateEquipmentStatusResource;

public class UpdateEquipmentStatusCommandFromResourceAssembler {
    public static UpdateEquipmentStatus toCommandFromResource(UpdateEquipmentStatusResource resource){
        return new UpdateEquipmentStatus(resource.id(), resource.status());
    }
}
