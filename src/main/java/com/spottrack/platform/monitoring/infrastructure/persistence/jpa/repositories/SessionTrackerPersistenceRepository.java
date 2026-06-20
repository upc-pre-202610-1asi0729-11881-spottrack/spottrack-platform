package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities.SessionTrackerPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionTrackerPersistenceRepository extends JpaRepository<SessionTrackerPersistenceEntity, Long> {
    Optional<SessionTrackerPersistenceEntity> findBySessionTrackerId(String sessionTrackerId);
    Optional<SessionTrackerPersistenceEntity> findByReservationId(String reservationId);
}
