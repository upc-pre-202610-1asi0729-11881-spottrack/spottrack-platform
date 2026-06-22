package com.spottrack.platform.monitoring.domain.repositories;

import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;

import java.util.List;
import java.util.Optional;

public interface SessionTrackerRepository {
    Optional<SessionTracker> findSessionByUuid(SessionTrackerId uuid);
    List<SessionTracker> findAllBySessionIsActive(SessionTrackerId uuid, boolean active);
    SessionTracker save(SessionTracker sessionTracker);

}
