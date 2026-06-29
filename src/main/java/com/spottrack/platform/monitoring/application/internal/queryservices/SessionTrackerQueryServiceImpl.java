package com.spottrack.platform.monitoring.application.internal.queryservices;

import com.spottrack.platform.monitoring.application.queryServices.SessionTrackerQueryService;
import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.domain.model.queries.GetActiveSessionTrackersQuery;
import com.spottrack.platform.monitoring.domain.model.queries.GetSessionTrackerByIdQuery;
import com.spottrack.platform.monitoring.domain.repositories.SessionTrackerRepository;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.SessionTrackerPersistenceAssembler;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.repositories.SessionTrackerPersistenceRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionTrackerQueryServiceImpl implements SessionTrackerQueryService {
    SessionTrackerRepository sessionTrackerRepository;
    SessionTrackerPersistenceRepository sessionTrackerPersistenceRepository;

    public SessionTrackerQueryServiceImpl(
            SessionTrackerRepository sessionTrackerRepository,
            SessionTrackerPersistenceRepository sessionTrackerPersistenceRepository) {
        this.sessionTrackerRepository = sessionTrackerRepository;
        this.sessionTrackerPersistenceRepository = sessionTrackerPersistenceRepository;
    }

    @Override
    public Result<List<SessionTracker>, ApplicationError> handle(GetActiveSessionTrackersQuery query) {
        try {
            var sessions = sessionTrackerPersistenceRepository.findAllBySessionIsActiveTrue()
                    .stream()
                    .map(SessionTrackerPersistenceAssembler::toDomainFromPersistence)
                    .toList();
            return Result.success(sessions);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("SessionTracker list", e.getMessage()));
        }
    }

    @Override
    public Optional<SessionTracker> handle(GetSessionTrackerByIdQuery query) {
        return sessionTrackerRepository.findSessionByUuid(query.sessionTrackerId());
    }
}
