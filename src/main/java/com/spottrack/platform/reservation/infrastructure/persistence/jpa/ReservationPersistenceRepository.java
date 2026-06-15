package com.spottrack.platform.reservation.infrastructure.persistence.jpa;

import com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities.ReservationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Reservation aggregate.
 * save(), findById(), findAll() etc. are provided by JpaRepository — no need to redeclare them.
 * Add custom query methods here only when needed (e.g. findByEquipmentIdAndStatus).
 */
public interface ReservationPersistenceRepository extends JpaRepository<ReservationPersistenceEntity, Long> {
    Optional<ReservationPersistenceEntity> findByUuid(String uuid);  // matches field name
    Optional<ReservationPersistenceEntity> findById(Long id);
}
