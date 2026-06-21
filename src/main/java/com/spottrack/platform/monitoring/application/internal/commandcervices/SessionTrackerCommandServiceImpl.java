package com.spottrack.platform.monitoring.application.internal.commandcervices;

import com.spottrack.platform.monitoring.application.commandServices.SessionTrackerCommandService;
import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.domain.model.commands.CameraCaptureMotionCommand;
import com.spottrack.platform.monitoring.domain.model.commands.CreateSessionTrackerCommand;
import com.spottrack.platform.monitoring.domain.model.commands.MotionSensorCaptureCommand;
import com.spottrack.platform.monitoring.domain.model.commands.VerifyUsageSessionCommand;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;
import com.spottrack.platform.monitoring.domain.repositories.SessionTrackerRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class SessionTrackerCommandServiceImpl implements SessionTrackerCommandService {
    private final SessionTrackerRepository sessionTrackerRepository;

    public SessionTrackerCommandServiceImpl(SessionTrackerRepository sessionTrackerRepository){
        this.sessionTrackerRepository = sessionTrackerRepository;
    }
    @Override
    public Result<SessionTracker, ApplicationError> handle(VerifyUsageSessionCommand command) {
        try {
            var session = sessionTrackerRepository.findSessionByUuid(new SessionTrackerId(command.sessionTrackerId()));
            if (session.isEmpty()) {
                return Result.failure(ApplicationError.notFound("sessionTracker", command.sessionTrackerId()));
            }
            var tracker = session.get();
            tracker.verifyUsageSession();
            return Result.success(tracker);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("sessionTracker", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("sessionTracker", e.getMessage()));
        }
    }

    @Override
    public Result<SessionTracker, ApplicationError> handle(CreateSessionTrackerCommand command) {
      try {
          var entity = new SessionTracker(command);
          var saved = sessionTrackerRepository.save(entity);
          return Result.success(saved);
      }
      catch (IllegalArgumentException exception) {
          return Result.failure(ApplicationError.validationError("sessionTracier", exception.getMessage()));
      }
      catch (Exception e) {
          return Result.failure(ApplicationError.validationError("sessionTracker", e.getMessage()));
      }
    }

    @Override
    public Result<SessionTracker, ApplicationError> handle(MotionSensorCaptureCommand command) {
        return null;
    }

    @Override
    public Result<SessionTracker, ApplicationError> handle(CameraCaptureMotionCommand command) {
        return null;
    }
}
