package com.spottrack.platform.reservation.application.internal.eventhandlers;

import com.spottrack.platform.reservation.application.commandServices.ReservationRequestCommandService;
import com.spottrack.platform.reservation.domain.model.commands.RequestEquipmentStatusChangeToAvailable;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestStatus;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationStatus;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.ReservationPersistenceRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.ReservationRequestPersistenceRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities.ReservationPersistenceEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationTimerExpiryScheduler {
    private final ReservationPersistenceRepository reservationPersistenceRepository;
    private final ReservationRequestPersistenceRepository reservationRequestPersistenceRepository;
    private final ReservationRequestCommandService reservationRequestCommandService;

    public ReservationTimerExpiryScheduler(ReservationPersistenceRepository reservationPersistenceRepository,
                                           ReservationRequestPersistenceRepository reservationRequestPersistenceRepository,
                                           ReservationRequestCommandService reservationRequestCommandService) {
        this.reservationPersistenceRepository = reservationPersistenceRepository;
        this.reservationRequestCommandService = reservationRequestCommandService;
        this.reservationRequestPersistenceRepository = reservationRequestPersistenceRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkReservationStatus(){
        List<ReservationPersistenceEntity> expiredReservations = reservationPersistenceRepository.findAllByStatusAndTimerExpiryIsNotNullAndTimerExpiryBefore(ReservationStatus.ACTIVE, LocalDateTime.now());

        expiredReservations.forEach(reservation -> {
            var equipmentId = reservation.getEquipmentId();
            var matchingRequest = reservationRequestPersistenceRepository
                    .findByEquipmentIdAndStatus(equipmentId, ReservationRequestStatus.SUBMITTED);

            matchingRequest.ifPresent(request -> {
                var command = new RequestEquipmentStatusChangeToAvailable(new ReservationRequestId(request.getUuid()));
                reservationRequestCommandService.handle(command);
            });
        });
    }

}
