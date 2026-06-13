package com.spottrack.platform.gym.interfaces.rest.transform;

import com.spottrack.platform.gym.interfaces.rest.resources.DecomissionEquipmentResource;
import com.spottrack.platform.maintenance.domain.model.commands.DecommissionEquipment;

public class DecomissionEquipmentCommandFromResourceAssembler {
    public static DecommissionEquipment toCommandFromResource(DecomissionEquipmentResource resource){
        return new DecommissionEquipment(resource.equipmentId());
    }
}
