package com.spottrack.platform.reservation.infrastructure.persistence.jpa;

import com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities.ReservationRequestPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequestPersistenceEntity, Long> {
    Optional<ReservationRequestPersistenceEntity> findByUuid(String uuid);
}
