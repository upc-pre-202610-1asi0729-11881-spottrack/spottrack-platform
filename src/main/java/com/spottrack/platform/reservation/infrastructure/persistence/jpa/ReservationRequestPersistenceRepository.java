package com.spottrack.platform.reservation.infrastructure.persistence.jpa;
import com.spottrack.platform.reservation.domain.model.aggregates.ReservationRequest;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestId;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities.ReservationRequestPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the ReservationRequest aggregate.
 */
public interface ReservationRequestPersistenceRepository extends JpaRepository<ReservationRequestPersistenceEntity, Long> {
    Optional<ReservationRequestPersistenceEntity> findById(Long id);
    Optional<ReservationRequestPersistenceEntity> findByUuid(String uuid);
}
