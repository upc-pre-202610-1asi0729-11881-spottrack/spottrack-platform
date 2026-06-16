package com.spottrack.platform.reservation.application.internal.commandservices;

import com.spottrack.platform.reservation.application.commandServices.ReservationRequestCommandService;
import com.spottrack.platform.reservation.domain.model.aggregates.ReservationRequest;
import com.spottrack.platform.reservation.domain.model.commands.RequestAlternativeEquipment;
import com.spottrack.platform.reservation.domain.model.commands.RequestEquipmentStatusChangeToAvailable;
import com.spottrack.platform.reservation.domain.model.commands.SubmitRequestOccupyEquipment;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.ReservationRequestRepository;
import com.spottrack.platform.reservation.infrastructure.persistence.jpa.assemblers.ReservationRequestPersistenceAssembler;
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
        var persistence = ReservationRequestPersistenceAssembler.toPersistenceFromDomain(request);
        var saved = reservationRequestRepository.save(persistence);
        return Result.success(ReservationRequestPersistenceAssembler.toDomainFromPersistence(saved));
    }

    @Transactional
    @Override
    public Result<ReservationRequest, ApplicationError> handle(RequestAlternativeEquipment command) {
        var found = reservationRequestRepository.findByUuid(command.requestId().uuid());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("ReservationRequest", command.requestId().uuid()));
        }
        var persistenceEntity = found.get();
        var request = ReservationRequestPersistenceAssembler.toDomainFromPersistence(persistenceEntity);
        request.requestAlternative(command);
        persistenceEntity.setStatus(request.getStatus());
        var saved = reservationRequestRepository.save(persistenceEntity);
        return Result.success(ReservationRequestPersistenceAssembler.toDomainFromPersistence(saved));
    }

    @Transactional
    @Override
    public Result<ReservationRequest, ApplicationError> handle(RequestEquipmentStatusChangeToAvailable command) {
        var found = reservationRequestRepository.findByUuid(command.requestId().uuid());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("ReservationRequest", command.requestId().uuid()));
        }
        var persistenceEntity = found.get();
        var request = ReservationRequestPersistenceAssembler.toDomainFromPersistence(persistenceEntity);
        request.requestEquipmentRelease(command);
        persistenceEntity.setStatus(request.getStatus());
        var saved = reservationRequestRepository.save(persistenceEntity);
        return Result.success(ReservationRequestPersistenceAssembler.toDomainFromPersistence(saved));
    }
}
