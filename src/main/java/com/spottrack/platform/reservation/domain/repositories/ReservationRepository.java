package com.spottrack.platform.reservation.domain.repositories;

import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.shared.application.result.ApplicationError;

import java.util.Optional;

public interface ReservationRepository {
    Optional<Reservation> findByUuid(String Id);
    Optional<Reservation> findById(Long Id);
    Reservation save(Reservation reservation);
}

