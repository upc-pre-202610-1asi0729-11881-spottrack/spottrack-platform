package com.spottrack.platform.reservation.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.domain.repositories.ReservationRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.ReservationPersistenceRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.assemblers.ReservationPersistenceAssembler;

import java.util.Optional;

public class ReservationRepositoryImpl implements ReservationRepository {
    ReservationPersistenceRepository reservationPersistenceRepository;
    public ReservationRepositoryImpl(ReservationPersistenceRepository reservationPersistenceRepository) {
        this.reservationPersistenceRepository = reservationPersistenceRepository;
    }

    @Override
    public Optional<Reservation> findByUuid(String id) {
        return reservationPersistenceRepository.findByUuid(id).map(ReservationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationPersistenceRepository.findById(id).map(ReservationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }
}
