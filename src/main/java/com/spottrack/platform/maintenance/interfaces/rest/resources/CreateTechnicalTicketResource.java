package com.spottrack.platform.maintenance.interfaces.rest.resources;

public record CreateTechnicalTicketResource(
        String equipmentId,
        String description,
        String priority,
        String type
) {}
