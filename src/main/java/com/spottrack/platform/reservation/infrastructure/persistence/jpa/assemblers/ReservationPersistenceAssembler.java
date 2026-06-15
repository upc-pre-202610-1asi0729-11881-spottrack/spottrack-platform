package com.spottrack.platform.reservation.infrastructure.persistence.jpa.assemblers;


import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities.ReservationPersistenceEntity;

public class ReservationPersistenceAssembler {
    public static Reservation toDomainFromPersistence(ReservationPersistenceEntity entity){
        return new Reservation(
                entity.getId().uuid(),
                entity.getClientId(),
                entity.getEquipmentId(),
                entity.getStatus().toString(),
                entity.getStartedAt(),
                entity.getTimerExpiry(),
                entity.getTimeInterval()
        );
    }

    public static ReservationPersistenceEntity toPersistenceFromDomain(Reservation entity){
        var persistence =  new ReservationPersistenceEntity();
        persistence.setId(entity.getId());
        persistence.setClientId(entity.getClientId());
        persistence.setEquipmentId(entity.getEquipmentId());
        persistence.setStatus(entity.getStatus());
        persistence.setTimeInterval(entity.getTimeInterval());
        persistence.setTimerExpiry(entity.getTimerExpiry());
        return persistence;
    }
}
