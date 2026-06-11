package com.spottrack.platform.routine.application.internal.commandservices;

import com.spottrack.platform.routine.application.commandservices.RoutineSessionCommandService;
import com.spottrack.platform.routine.domain.model.aggregates.RoutineSession;
import com.spottrack.platform.routine.domain.model.commands.CompleteRoutineCommand;
import com.spottrack.platform.routine.domain.model.commands.MarkRoutineMissedCommand;
import com.spottrack.platform.routine.domain.model.commands.StartRoutineCommand;
import com.spottrack.platform.routine.domain.repositories.RoutineSessionRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class RoutineSessionCommandServiceImpl implements RoutineSessionCommandService {

    private final RoutineSessionRepository routineSessionRepository;

    public RoutineSessionCommandServiceImpl(RoutineSessionRepository routineSessionRepository) {
        this.routineSessionRepository = routineSessionRepository;
    }

    @Override
    public Result<RoutineSession, ApplicationError> handle(StartRoutineCommand command) {
        try {
            var session = new RoutineSession(command);
            var savedSession = routineSessionRepository.save(session);
            return Result.success(savedSession);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("RoutineSession", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("RoutineSession creation", e.getMessage()));
        }
    }

    @Override
    public Result<RoutineSession, ApplicationError> handle(CompleteRoutineCommand command) {
        try {
            var session = routineSessionRepository.findById(command.routineSessionId());
            if (session.isEmpty()) {
                return Result.failure(ApplicationError.notFound("RoutineSession", command.routineSessionId().toString()));
            }
            session.get().complete();
            var savedSession = routineSessionRepository.save(session.get());
            return Result.success(savedSession);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("RoutineSession", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("RoutineSession completion", e.getMessage()));
        }
    }

    @Override
    public Result<RoutineSession, ApplicationError> handle(MarkRoutineMissedCommand command) {
        try {
            var session = routineSessionRepository.findById(command.routineSessionId());
            if (session.isEmpty()) {
                return Result.failure(ApplicationError.notFound("RoutineSession", command.routineSessionId().toString()));
            }
            session.get().markMissed();
            var savedSession = routineSessionRepository.save(session.get());
            return Result.success(savedSession);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("RoutineSession", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("RoutineSession missed marking", e.getMessage()));
        }
    }
}
