package com.spottrack.platform.reservation.infrastructure.persistence.jpa;

import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Reservation aggregate.
 * save(), findById(), findAll() etc. are provided by JpaRepository — no need to redeclare them.
 * Add custom query methods here only when needed (e.g. findByEquipmentIdAndStatus).
 */
public interface giutReservationRepository extends JpaRepository<Reservation, ReservationId> {
}
