package com.spottrack.platform.reservation.application.internal.commandservices;

import com.spottrack.platform.reservation.application.commandServices.ReservationCommandService;
import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.domain.model.commands.CancelReservation;
import com.spottrack.platform.reservation.domain.model.commands.EndReservation;
import com.spottrack.platform.reservation.domain.model.commands.InitiateExpressReservation;
import com.spottrack.platform.reservation.domain.model.commands.StartReservationTimer;
import com.spottrack.platform.reservation.domain.repositories.ReservationRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final ReservationRepository reservationRepository;

    public ReservationCommandServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Result<Reservation, ApplicationError> handle(InitiateExpressReservation command) {
        try {
            var reservation = new Reservation(command);
            var savedReservation = reservationRepository.save(reservation);
            return Result.success(savedReservation);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Reservation", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Reservation creation", e.getMessage()));
        }
    }

    @Override
    public Result<Reservation, ApplicationError> handle(CancelReservation command) {
        try {
            var found = reservationRepository.findByUuid(command.reservationId().uuid());
            if (found.isEmpty())
                return Result.failure(ApplicationError.notFound("Reservation", command.reservationId().uuid()));
            var domain = found.get();
            domain.cancel();
            return Result.success(reservationRepository.save(domain));
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.validationError("Reservation", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Reservation cancellation", e.getMessage()));
        }
    }

    @Override
    public Result<Reservation, ApplicationError> handle(StartReservationTimer command) {
        try {
            var found = reservationRepository.findByUuid(command.reservationId().uuid());
            if (found.isEmpty())
                return Result.failure(ApplicationError.notFound("Reservation", command.reservationId().uuid()));
            var domain = found.get();
            domain.startTimer(command.durationMinutes());
            return Result.success(reservationRepository.save(domain));
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.validationError("Reservation", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Reservation timer start", e.getMessage()));
        }
    }

    @Override
    public Result<Reservation, ApplicationError> handle(EndReservation command) {
        try {
            var found = reservationRepository.findByUuid(command.reservationId().uuid());
            if (found.isEmpty())
                return Result.failure(ApplicationError.notFound("Reservation", command.reservationId().uuid()));
            var domain = found.get();
            domain.end();
            return Result.success(reservationRepository.save(domain));
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.validationError("Reservation", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Reservation end", e.getMessage()));
        }
    }
}