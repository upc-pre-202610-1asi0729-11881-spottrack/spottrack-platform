package com.spottrack.platform.reservation.infrastructure.persistence.jpa;

import com.spottrack.platform.reservation.domain.model.aggregates.ReservationRequest;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the ReservationRequest aggregate.
 */
public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, ReservationRequestId> {
    Optional<ReservationRequest> findById(Long id);
    Optional<ReservationRequest> findByUuid(String uuid);
}
