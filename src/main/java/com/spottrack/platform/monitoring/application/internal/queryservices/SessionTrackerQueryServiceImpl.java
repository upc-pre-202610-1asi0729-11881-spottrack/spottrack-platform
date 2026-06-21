package com.spottrack.platform.monitoring.application.internal.queryservices;

import com.spottrack.platform.monitoring.application.queryServices.SessionTrackerQueryService;
import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.domain.model.queries.GetSessionTrackerByIdQuery;
import com.spottrack.platform.monitoring.domain.repositories.SessionTrackerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionTrackerQueryServiceImpl implements SessionTrackerQueryService {
    SessionTrackerRepository sessionTrackerRepository;
    public SessionTrackerQueryServiceImpl(SessionTrackerRepository sessionTrackerRepository){
        this.sessionTrackerRepository = sessionTrackerRepository;
    }

    @Override
    public Optional<SessionTracker> handle(GetSessionTrackerByIdQuery query) {
        return sessionTrackerRepository.findSessionByUuid(query.sessionTrackerId());
    }
}
