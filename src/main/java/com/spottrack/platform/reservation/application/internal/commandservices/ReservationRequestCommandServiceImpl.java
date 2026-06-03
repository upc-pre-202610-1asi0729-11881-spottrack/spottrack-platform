package com.spottrack.platform.reservation.application.internal.commandservices;

import com.spottrack.platform.reservation.application.commandServices.ReservationRequestCommandService;
import com.spottrack.platform.reservation.domain.model.aggregates.ReservationRequest;
import com.spottrack.platform.reservation.domain.model.commands.RequestAlternativeEquipment;
import com.spottrack.platform.reservation.domain.model.commands.RequestEquipmentStatusChangeToAvailable;
import com.spottrack.platform.reservation.domain.model.commands.SubmitRequestOccupyEquipment;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.ReservationRequestRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationRequestCommandServiceImpl implements ReservationRequestCommandService {

    private final ReservationRequestRepository reservationRequestRepository;

    public ReservationRequestCommandServiceImpl(ReservationRequestRepository reservationRequestRepository) {
        this.reservationRequestRepository = reservationRequestRepository;
    }

    /**
     * Submits a new equipment occupation request.
     * This is the entry point for the standard reservation flow (not express).
     */
    @Transactional
    @Override
    public Result<ReservationRequest, ApplicationError> handle(SubmitRequestOccupyEquipment command) {
        var request = new ReservationRequest(command);
        var saved = reservationRequestRepository.save(request);
        return Result.success(saved);
    }

    /**
     * Marks that the client wants a different piece of equipment.
     * The aggregate enforces that only SUBMITTED requests can transition to ALTERNATIVE_REQUESTED.
     */
    @Transactional
    @Override
    public Result<ReservationRequest, ApplicationError> handle(RequestAlternativeEquipment command) {
        var found = reservationRequestRepository.findById(command.requestId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("ReservationRequest", command.requestId().uuid()));
        }
        var request = found.get();
        request.requestAlternative(command);
        var saved = reservationRequestRepository.save(request);
        return Result.success(saved);
    }

    /**
     * Signals that the equipment should be released back to AVAILABLE.
     * Triggered at the end of a reservation lifecycle.
     */
    @Transactional
    @Override
    public Result<ReservationRequest, ApplicationError> handle(RequestEquipmentStatusChangeToAvailable command) {
        var found = reservationRequestRepository.findById(command.requestId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("ReservationRequest", command.requestId().uuid()));
        }
        var request = found.get();
        request.requestEquipmentRelease(command);
        var saved = reservationRequestRepository.save(request);
        return Result.success(saved);
    }
}
