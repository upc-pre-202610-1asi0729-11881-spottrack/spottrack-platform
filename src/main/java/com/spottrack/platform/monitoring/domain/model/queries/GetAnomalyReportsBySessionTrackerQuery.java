package com.spottrack.platform.monitoring.domain.model.queries;

public record GetAnomalyReportsBySessionTrackerQuery(String sessionTrackerId) {
    public GetAnomalyReportsBySessionTrackerQuery {
        if (sessionTrackerId == null || sessionTrackerId.isBlank()) {
            throw new IllegalArgumentException("sessionTrackerId must not be blank");
        }
    }
}
