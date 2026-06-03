package com.spottrack.platform.maintenance.interfaces.rest.transform;

import com.spottrack.platform.maintenance.domain.model.commands.CreateTechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceId;
import com.spottrack.platform.maintenance.interfaces.rest.resources.CreateTechnicalTicketResource;

public class CreateTechnicalTicketCommandFromResourceAssembler {

    public static CreateTechnicalTicket toCommandFromResource(CreateTechnicalTicketResource resource) {
        return new CreateTechnicalTicket(
                new MaintenanceId(resource.maintenanceId()),
                resource.equipmentId(),
                resource.description()
        );
    }
}
