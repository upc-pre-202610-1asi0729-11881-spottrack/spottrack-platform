package com.spottrack.platform.reservation.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.reservation.domain.model.aggregates.ReservationRequest;
import com.spottrack.platform.reservation.domain.repositories.ReservationRequestRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.ReservationRequestPersistenceRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.assemblers.ReservationRequestPersistenceAssembler;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities.ReservationRequestPersistenceEntity;

import java.util.Optional;

public class ResevationRequestRepositoryImpl implements ReservationRequestRepository {
    ReservationRequestPersistenceRepository reservationRequestPersistenceRepository;
    public ResevationRequestRepositoryImpl(ReservationRequestPersistenceRepository reservationRequestPersistenceRepository){
        this.reservationRequestPersistenceRepository = reservationRequestPersistenceRepository;
    }
    @Override
    public Optional<ReservationRequest> findById(Long id) {
        var entity = reservationRequestPersistenceRepository.findById(id);
        var domain = entity.map(ReservationRequestPersistenceAssembler::toDomainFromPersistence);
        return domain;
    }

    @Override
    public Optional<ReservationRequest> findByUuid(String uuid) {
        return reservationRequestPersistenceRepository.findByUuid(uuid).map(ReservationRequestPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<ReservationRequest> save(ReservationRequest reservationRequestEntity) {
        return Optional.empty();
    }
}
