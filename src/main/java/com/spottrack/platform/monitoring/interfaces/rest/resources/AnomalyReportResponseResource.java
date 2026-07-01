package com.spottrack.platform.monitoring.interfaces.rest.resources;

public record AnomalyReportResponseResource(
        String anomalyReportId,
        String sessionTrackerId,
        String anomalyType,
        String description,
        String reportedAt,
        boolean resolved
) {
}
