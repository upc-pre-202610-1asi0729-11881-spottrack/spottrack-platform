package com.spottrack.platform.reservation.interfaces.rest.transform;

import com.spottrack.platform.reservation.domain.model.commands.SubmitRequestOccupyEquipment;
import com.spottrack.platform.reservation.domain.model.valueobjects.ClientId;
import com.spottrack.platform.reservation.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.reservation.interfaces.rest.resources.SubmitRequestOccupyEquipmentResource;

public class SubmitRequestOccupyEquipmentCommandFromResourceAssembler {

    public static SubmitRequestOccupyEquipment toCommandFromResource(SubmitRequestOccupyEquipmentResource resource) {
        return new SubmitRequestOccupyEquipment(
                new ClientId(resource.clientId()),
                new EquipmentId(resource.equipmentId())
        );
    }
}
