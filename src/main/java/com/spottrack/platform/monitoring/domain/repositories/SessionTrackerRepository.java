package com.spottrack.platform.monitoring.domain.repositories;

import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;

import java.util.Optional;

public interface SessionTrackerRepository {
    Optional<SessionTracker> findSessionByUuid(SessionTrackerId uuid);
    SessionTracker save(SessionTracker sessionTracker);
}
