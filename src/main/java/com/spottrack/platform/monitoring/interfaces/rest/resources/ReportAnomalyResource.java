package com.spottrack.platform.monitoring.interfaces.rest.resources;

public record ReportAnomalyResource(String sessionTrackerId, String anomalyType, String description) {
}
