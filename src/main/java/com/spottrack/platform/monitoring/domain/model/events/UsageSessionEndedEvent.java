package com.spottrack.platform.monitoring.domain.model.events;

import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;

public record UsageSessionEndedEvent(SessionTrackerId sessionTrackerId) {
}
