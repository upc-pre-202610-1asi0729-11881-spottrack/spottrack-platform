package com.spottrack.platform.reservation.application.internal.commandservices;

import com.spottrack.platform.reservation.application.commandServices.ReservationCommandService;
import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.domain.model.commands.CancelReservation;
import com.spottrack.platform.reservation.domain.model.commands.EndReservation;
import com.spottrack.platform.reservation.domain.model.commands.InitiateExpressReservation;
import com.spottrack.platform.reservation.domain.model.commands.StartReservationTimer;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.ReservationRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final ReservationRepository reservationRepository;

    public ReservationCommandServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Creates a new express reservation directly — no prior request needed.
     * Equipment bounded context should separately be notified to mark equipment OCCUPIED.
     */
    @Transactional
    @Override
    public Result<Reservation, ApplicationError> handle(InitiateExpressReservation command) {
        var reservation = new Reservation(command);
        var saved = reservationRepository.save(reservation);
        return Result.success(saved);
    }

    /**
     * Cancels an active reservation. Returns NOT_FOUND if the ID doesn't exist.
     */
    @Transactional
    @Override
    public Result<Reservation, ApplicationError> handle(CancelReservation command) {
        var found = reservationRepository.findById(command.reservationId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Reservation", command.reservationId().uuid()));
        }
        var reservation = found.get();
        reservation.cancel();
        var saved = reservationRepository.save(reservation);
        return Result.success(saved);
    }

    /**
     * Starts the countdown timer for an existing active reservation.
     */
    @Transactional
    @Override
    public Result<Reservation, ApplicationError> handle(StartReservationTimer command) {
        var found = reservationRepository.findById(command.reservationId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Reservation", command.reservationId().uuid()));
        }
        var reservation = found.get();
        reservation.startTimer(command.durationMinutes());
        var saved = reservationRepository.save(reservation);
        return Result.success(saved);
    }

    /**
     * Client explicitly ends the reservation before the timer expires.
     */
    @Transactional
    @Override
    public Result<Reservation, ApplicationError> handle(EndReservation command) {
        var found = reservationRepository.findById(command.reservationId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Reservation", command.reservationId().uuid()));
        }
        var reservation = found.get();
        reservation.end();
        var saved = reservationRepository.save(reservation);
        return Result.success(saved);
    }
}
