package com.spottrack.platform.reservation.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.domain.repositories.ReservationRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.ReservationPersistenceRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.assemblers.ReservationPersistenceAssembler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationPersistenceRepository reservationPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ReservationRepositoryImpl(ReservationPersistenceRepository reservationPersistenceRepository,
                                     ApplicationEventPublisher eventPublisher) {
        this.reservationPersistenceRepository = reservationPersistenceRepository;
        this.eventPublisher = eventPublisher;
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
        boolean isNew = reservationPersistenceRepository.findByUuid(reservation.getId().uuid()).isEmpty();
        var savedEntity = reservationPersistenceRepository.save(ReservationPersistenceAssembler.toPersistenceFromDomain(reservation));
        var savedReservation = ReservationPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedReservation.onInitiated();
            savedReservation.domainEvents().forEach(eventPublisher::publishEvent);
            savedReservation.clearDomainEvents();
        } else {
            reservation.domainEvents().forEach(eventPublisher::publishEvent);
            reservation.clearDomainEvents();
        }
        return savedReservation;
    }
}
