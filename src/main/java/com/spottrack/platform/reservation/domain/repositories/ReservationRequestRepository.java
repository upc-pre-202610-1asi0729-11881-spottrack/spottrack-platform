package com.spottrack.platform.reservation.domain.repositories;

import com.spottrack.platform.reservation.domain.model.aggregates.ReservationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRequestRepository  {
    Optional<ReservationRequest> findById(Long id);
    Optional<ReservationRequest> findByUuid(String uuid);
    Optional<ReservationRequest> save(ReservationRequest reservationRequestEntity);
}
