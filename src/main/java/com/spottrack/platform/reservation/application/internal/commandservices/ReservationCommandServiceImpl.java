package com.spottrack.platform.reservation.application.internal.commandservices;

import com.spottrack.platform.reservation.application.commandServices.ReservationCommandService;
import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.domain.model.commands.CancelReservation;
import com.spottrack.platform.reservation.domain.model.commands.EndReservation;
import com.spottrack.platform.reservation.domain.model.commands.InitiateExpressReservation;
import com.spottrack.platform.reservation.domain.model.commands.StartReservationTimer;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.ReservationPersistenceRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.assemblers.ReservationPersistenceAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final ReservationPersistenceRepository reservationPersistenceRepository;

    public ReservationCommandServiceImpl(ReservationPersistenceRepository reservationPersistenceRepository) {
        this. reservationPersistenceRepository = reservationPersistenceRepository;
    }

    /**
     * Creates a new express reservation directly — no prior request needed.
     * Equipment bounded context should separately be notified to mark equipment OCCUPIED.
     */
    @Transactional
    @Override
    public Result<Reservation, ApplicationError> handle(InitiateExpressReservation command) {
        var reservation = new Reservation(command);
        var entity = ReservationPersistenceAssembler.toPersistenceFromDomain(reservation);
        var savedEntity = reservationPersistenceRepository.save(entity);
        var savedDomain = ReservationPersistenceAssembler.toDomainFromPersistence(savedEntity);
        return Result.success(savedDomain);
    }

    @Transactional
    @Override
    public Result<Reservation, ApplicationError> handle(CancelReservation command) {
        var found = reservationPersistenceRepository.findByUuid(command.reservationId().uuid());
        if (found.isEmpty())
            return Result.failure(ApplicationError.notFound("Reservation", command.reservationId().uuid()));

        var domain = ReservationPersistenceAssembler.toDomainFromPersistence(found.get());
        domain.cancel();
        var savedEntity = reservationPersistenceRepository.save(ReservationPersistenceAssembler.toPersistenceFromDomain(domain));
        return Result.success(ReservationPersistenceAssembler.toDomainFromPersistence(savedEntity));
    }

    @Transactional
    @Override
    public Result<Reservation, ApplicationError> handle(StartReservationTimer command) {
        var found = reservationPersistenceRepository.findByUuid(command.reservationId().uuid());
        if (found.isEmpty())
            return Result.failure(ApplicationError.notFound("Reservation", command.reservationId().uuid()));

        var domain = ReservationPersistenceAssembler.toDomainFromPersistence(found.get());
        domain.startTimer(command.durationMinutes());
        var savedEntity = reservationPersistenceRepository.save(ReservationPersistenceAssembler.toPersistenceFromDomain(domain));
        return Result.success(ReservationPersistenceAssembler.toDomainFromPersistence(savedEntity));
    }

    @Transactional
    @Override
    public Result<Reservation, ApplicationError> handle(EndReservation command) {
        var found = reservationPersistenceRepository.findByUuid(command.reservationId().uuid());
        if (found.isEmpty())
            return Result.failure(ApplicationError.notFound("Reservation", command.reservationId().uuid()));

        var domain = ReservationPersistenceAssembler.toDomainFromPersistence(found.get());
        domain.end();
        var savedEntity = reservationPersistenceRepository.save(ReservationPersistenceAssembler.toPersistenceFromDomain(domain));
        return Result.success(ReservationPersistenceAssembler.toDomainFromPersistence(savedEntity));
    }
}