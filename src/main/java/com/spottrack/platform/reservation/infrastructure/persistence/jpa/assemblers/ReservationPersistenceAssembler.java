package com.spottrack.platform.reservation.infrastructure.persistence.jpa.assemblers;


import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.domain.model.valueobjects.TimeInterval;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities.ReservationPersistenceEntity;


public class ReservationPersistenceAssembler {
    public static Reservation toDomainFromPersistence(ReservationPersistenceEntity entity){
        return new Reservation(
                entity.getUuid(),
                entity.getClientId(),
                entity.getEquipmentId(),
                entity.getStatus().toString(),
                entity.getStartedAt(),
                entity.getTimerExpiry(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }

    public static ReservationPersistenceEntity toPersistenceFromDomain(Reservation entity){
        var persistence =  new ReservationPersistenceEntity();
        persistence.setUuid(entity.getId().uuid());
        persistence.setClientId(entity.getClientId());
        persistence.setEquipmentId(entity.getEquipmentId());
        persistence.setStatus(entity.getStatus());
        persistence.setStartTime(entity.getTimeInterval().startTime());
        persistence.setEndTime(entity.getTimeInterval().endTime());
        persistence.setTimerExpiry(entity.getTimerExpiry());
        return persistence;
    }
}
