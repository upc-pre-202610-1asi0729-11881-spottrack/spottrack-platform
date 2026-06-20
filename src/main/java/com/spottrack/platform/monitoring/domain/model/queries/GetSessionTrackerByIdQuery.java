package com.spottrack.platform.monitoring.domain.model.queries;

import com.spottrack.platform.monitoring.domain.model.valueObjects.SessionTrackerId;

public record GetSessionTrackerByIdQuery(SessionTrackerId sessionTrackerId) {
}
