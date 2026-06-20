package com.spottrack.platform.maintenance.interfaces.rest.resources;

public record RegisterMaintenanceCompletionResource(
        String maintenanceId,
        String notes
) {}
