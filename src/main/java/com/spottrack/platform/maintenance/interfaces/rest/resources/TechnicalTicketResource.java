package com.spottrack.platform.maintenance.interfaces.rest.resources;

import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketStatus;

public record TechnicalTicketResource(
        String id,
        String maintenanceId,
        String equipmentId,
        String technicianId,
        String description,
        TicketStatus ticketStatus,
        MaintenanceStatus maintenanceStatus
) {}
