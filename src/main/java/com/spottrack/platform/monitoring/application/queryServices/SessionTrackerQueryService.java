package com.spottrack.platform.monitoring.application.queryServices;

import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.domain.model.queries.GetActiveSessionTrackersQuery;
import com.spottrack.platform.monitoring.domain.model.queries.GetSessionTrackerByIdQuery;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

import java.util.List;
import java.util.Optional;

public interface SessionTrackerQueryService {
    Result<List<SessionTracker>, ApplicationError> handle(GetActiveSessionTrackersQuery query);
    Optional<SessionTracker> handle(GetSessionTrackerByIdQuery query);

}
