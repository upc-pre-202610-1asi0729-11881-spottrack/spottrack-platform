package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities.SessionTrackerPersistenceEntity;

public class SessionTrackerPersistenceAssembler {
    public static SessionTracker toDomainFromPersistence(SessionTrackerPersistenceEntity entity){
        return new SessionTracker(entity.getId(), entity.getSessionTrackerId(), entity.getReservationId(), entity.getContinuousActivity(), entity.getSeconds(), entity.isSessionIsActive(), entity.isSessionIsInactive(), entity.getLastActivityAt());
    }

    public static SessionTrackerPersistenceEntity toPersistenceFromDomain(SessionTracker entity){
        var persistenceEntity = new SessionTrackerPersistenceEntity();
        persistenceEntity.setId(entity.getId());
        persistenceEntity.setSessionTrackerId(entity.getSessionTrackerId().uuid());
        persistenceEntity.setReservationId(entity.getReservationId().uuid());
        persistenceEntity.setContinuousActivity(entity.getUsageActivity().continuousActivity());
        persistenceEntity.setSeconds((entity.getUsageActivity().seconds()));
        persistenceEntity.setSessionIsActive(entity.isSessionIsActive());
        persistenceEntity.setSessionIsInactive(entity.isSessionIsInactive());
        persistenceEntity.setLastActivityAt(entity.getLastActivityAt());
        return persistenceEntity;
    }
}
