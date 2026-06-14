package com.spottrack.platform.gym.interfaces.rest.transform;

import com.spottrack.platform.gym.domain.model.commands.DecomissionEquipment;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.gym.interfaces.rest.resources.DecomissionEquipmentResource;

public class DecomissionEquipmentCommandFromResourceAssembler {
    public static DecomissionEquipment toCommandFromResource(DecomissionEquipmentResource resource) {
        return new DecomissionEquipment(resource.equipmentId(), EquipmentStatus.DECOMMISSIONED);
    }
}
