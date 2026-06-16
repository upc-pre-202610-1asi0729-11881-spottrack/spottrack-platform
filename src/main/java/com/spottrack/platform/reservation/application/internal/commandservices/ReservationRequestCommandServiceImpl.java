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

    @Transactional
    @Override
    public Result<ReservationRequest, ApplicationError> handle(SubmitRequestOccupyEquipment command) {
        try {
            var request = new ReservationRequest(command);
            var entity = ReservationRequestPersistenceAssembler.toPersistenceFromDomain(request);
            var saved = reservationRequestRepository.save(entity);
            return Result.success(ReservationRequestPersistenceAssembler.toDomainFromPersistence(saved));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("ReservationRequest", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("ReservationRequest creation", e.getMessage()));
        }
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
