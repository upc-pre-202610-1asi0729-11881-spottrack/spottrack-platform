package com.spottrack.platform.reservation.interfaces.rest.transform;

import com.spottrack.platform.reservation.domain.model.commands.SubmitRequestOccupyEquipment;
import com.spottrack.platform.reservation.interfaces.rest.resources.SubmitRequestOccupyEquipmentResource;

public class SubmitRequestOccupyEquipmentCommandFromResourceAssembler {

    public static SubmitRequestOccupyEquipment toCommandFromResource(SubmitRequestOccupyEquipmentResource resource) {
        return new SubmitRequestOccupyEquipment(resource.clientId(), resource.equipmentId());
    }
}
