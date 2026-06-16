package com.spottrack.platform.reservation.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.reservation.domain.model.aggregates.ReservationRequest;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities.ReservationRequestPersistenceEntity;

public class ReservationRequestPersistenceAssembler {

    public static ReservationRequest toDomainFromPersistence(ReservationRequestPersistenceEntity entity) {
        return new ReservationRequest(
                entity.getUuid(),
                entity.getClientId(),
                entity.getEquipmentId(),
                entity.getStatus(),
                entity.getRequestedAt()
        );
    }

    public static ReservationRequestPersistenceEntity toPersistenceFromDomain(ReservationRequest domain) {
        var persistence = new ReservationRequestPersistenceEntity();
        persistence.setUuid(domain.getId().uuid());
        persistence.setClientId(domain.getClientId());
        persistence.setEquipmentId(domain.getEquipmentId());
        persistence.setStatus(domain.getStatus());
        persistence.setRequestedAt(domain.getRequestedAt());
        return persistence;
    }
}
