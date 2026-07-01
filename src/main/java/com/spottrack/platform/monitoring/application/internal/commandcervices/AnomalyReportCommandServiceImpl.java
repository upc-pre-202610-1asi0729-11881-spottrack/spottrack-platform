package com.spottrack.platform.monitoring.application.internal.commandcervices;

import com.spottrack.platform.monitoring.application.commandservices.AnomalyReportCommandService;
import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;
import com.spottrack.platform.monitoring.domain.model.commands.ReportAnomalyManuallyCommand;
import com.spottrack.platform.monitoring.domain.repositories.AnomalyReportRepository;
import com.spottrack.platform.monitoring.domain.repositories.SessionTrackerRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class AnomalyReportCommandServiceImpl implements AnomalyReportCommandService {

    private final SessionTrackerRepository sessionTrackerRepository;
    private final AnomalyReportRepository anomalyReportRepository;

    public AnomalyReportCommandServiceImpl(
            SessionTrackerRepository sessionTrackerRepository,
            AnomalyReportRepository anomalyReportRepository) {
        this.sessionTrackerRepository = sessionTrackerRepository;
        this.anomalyReportRepository = anomalyReportRepository;
    }

    @Override
    public Result<AnomalyReport, ApplicationError> handle(ReportAnomalyManuallyCommand command) {
        try {
            var session = sessionTrackerRepository.findSessionByUuid(command.sessionTrackerId());
            if (session.isEmpty()) {
                return Result.failure(ApplicationError.notFound("SessionTracker", command.sessionTrackerId().uuid()));
            }
            var saved = anomalyReportRepository.save(new AnomalyReport(command));
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("anomaly", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("anomaly", e.getMessage()));
        }
    }
}
