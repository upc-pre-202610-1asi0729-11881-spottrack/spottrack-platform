package com.spottrack.platform.routine.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.routine.domain.model.aggregates.RoutineSession;
import com.spottrack.platform.routine.domain.model.valueobjects.ClientId;
import com.spottrack.platform.routine.domain.model.valueobjects.RoutineSessionStatus;
import com.spottrack.platform.routine.infrastructure.persistence.jpa.entities.RoutineSessionPersistenceEntity;

public final class RoutineSessionPersistenceAssembler {

    private RoutineSessionPersistenceAssembler() {}

    public static RoutineSession toDomainFromPersistence(RoutineSessionPersistenceEntity entity) {
        return new RoutineSession(
                entity.getId(),
                entity.getRoutineId(),
                new ClientId(entity.getClientId()),
                RoutineSessionStatus.valueOf(entity.getStatus()),
                entity.getStartedAt()
        );
    }

    public static RoutineSessionPersistenceEntity toPersistenceFromDomain(RoutineSession session) {
        var entity = new RoutineSessionPersistenceEntity();
        entity.setId(session.getId());
        entity.setRoutineId(session.getRoutineId());
        entity.setClientId(session.getClientId().clientId());
        entity.setStatus(session.getStatus().name());
        entity.setStartedAt(session.getStartedAt());
        return entity;
    }
}
