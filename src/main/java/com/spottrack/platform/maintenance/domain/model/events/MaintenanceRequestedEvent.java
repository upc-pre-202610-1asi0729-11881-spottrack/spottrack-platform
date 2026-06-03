package com.spottrack.platform.maintenance.domain.model.events;

public record MaintenanceRequestedEvent(String maintenanceId, String equipmentId, String requestedBy) {}
