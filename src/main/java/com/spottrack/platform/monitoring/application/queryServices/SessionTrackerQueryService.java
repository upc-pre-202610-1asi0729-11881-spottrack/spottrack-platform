package com.spottrack.platform.monitoring.application.queryServices;

import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.domain.model.queries.GetSessionTrackerByIdQuery;

import java.util.Optional;

public interface SessionTrackerQueryService {
    Optional<SessionTracker> handle(GetSessionTrackerByIdQuery query);

}
