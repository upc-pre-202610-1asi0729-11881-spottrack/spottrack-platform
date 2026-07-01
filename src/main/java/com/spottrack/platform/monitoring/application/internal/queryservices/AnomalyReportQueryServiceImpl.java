package com.spottrack.platform.monitoring.application.internal.queryservices;

import com.spottrack.platform.monitoring.application.queryservices.AnomalyReportQueryService;
import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;
import com.spottrack.platform.monitoring.domain.model.queries.GetAnomalyReportsBySessionTrackerQuery;
import com.spottrack.platform.monitoring.domain.model.queries.GetAnomalyReportsQuery;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;
import com.spottrack.platform.monitoring.domain.repositories.SessionTrackerRepository;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.AnomalyReportPersistenceAssembler;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.repositories.AnomalyReportPersistenceRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnomalyReportQueryServiceImpl implements AnomalyReportQueryService {

    private final AnomalyReportPersistenceRepository anomalyReportPersistenceRepository;
    private final SessionTrackerRepository sessionTrackerRepository;

    public AnomalyReportQueryServiceImpl(
            AnomalyReportPersistenceRepository anomalyReportPersistenceRepository,
            SessionTrackerRepository sessionTrackerRepository) {
        this.anomalyReportPersistenceRepository = anomalyReportPersistenceRepository;
        this.sessionTrackerRepository = sessionTrackerRepository;
    }

    @Override
    public Result<List<AnomalyReport>, ApplicationError> handle(GetAnomalyReportsQuery query) {
        try {
            var reports = anomalyReportPersistenceRepository.findAll()
                    .stream()
                    .map(AnomalyReportPersistenceAssembler::toDomainFromPersistence)
                    .toList();
            return Result.success(reports);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("anomaly reports list", e.getMessage()));
        }
    }

    @Override
    public Result<List<AnomalyReport>, ApplicationError> handle(GetAnomalyReportsBySessionTrackerQuery query) {
        try {
            var session = sessionTrackerRepository.findSessionByUuid(
                    new SessionTrackerId(query.sessionTrackerId()));
            if (session.isEmpty()) {
                return Result.failure(
                        ApplicationError.notFound("SessionTracker", query.sessionTrackerId()));
            }
            var reports = anomalyReportPersistenceRepository
                    .findBySessionTrackerId(query.sessionTrackerId())
                    .stream()
                    .map(AnomalyReportPersistenceAssembler::toDomainFromPersistence)
                    .toList();
            return Result.success(reports);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("anomaly reports by session", e.getMessage()));
        }
    }
}
