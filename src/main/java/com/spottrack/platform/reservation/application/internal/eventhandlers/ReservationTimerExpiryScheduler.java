package com.spottrack.platform.reservation.application.internal.eventhandlers;

import com.spottrack.platform.reservation.application.commandServices.ReservationCommandService;
import com.spottrack.platform.reservation.domain.model.commands.EndReservation;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationStatus;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.repositories.ReservationPersistenceRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities.ReservationPersistenceEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Backstop for ReservationTimerStartedEventHandler's precise TaskScheduler-based
 * expiry: that task lives in memory only, so a restart between timer-start and
 * expiry would otherwise leave the reservation ACTIVE forever. This polls for
 * anything still ACTIVE past its timerExpiry and ends it the same way the
 * primary path does — the ACTIVE filter means it never re-matches a reservation
 * the primary path already ended, so there's no double-handling.
 */
@Service
public class ReservationTimerExpiryScheduler {
    private final ReservationPersistenceRepository reservationPersistenceRepository;
    private final ReservationCommandService reservationCommandService;

    public ReservationTimerExpiryScheduler(ReservationPersistenceRepository reservationPersistenceRepository,
                                           ReservationCommandService reservationCommandService) {
        this.reservationPersistenceRepository = reservationPersistenceRepository;
        this.reservationCommandService = reservationCommandService;
    }

    @Scheduled(fixedRate = 60000)
    public void checkReservationStatus(){
        List<ReservationPersistenceEntity> expiredReservations = reservationPersistenceRepository.findAllByStatusAndTimerExpiryIsNotNullAndTimerExpiryBefore(ReservationStatus.ACTIVE, LocalDateTime.now());

        expiredReservations.forEach(reservation ->
                reservationCommandService.handle(new EndReservation(new ReservationId(reservation.getUuid()))));
    }

}
