package com.spottrack.platform.monitoring.application.internal.queryservices;

import com.spottrack.platform.monitoring.application.queryservices.AnomalyReportQueryService;
import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;
import com.spottrack.platform.monitoring.domain.model.queries.GetAnomalyReportsQuery;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.AnomalyReportPersistenceAssembler;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.repositories.AnomalyReportPersistenceRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnomalyReportQueryServiceImpl implements AnomalyReportQueryService {

    private final AnomalyReportPersistenceRepository anomalyReportPersistenceRepository;

    public AnomalyReportQueryServiceImpl(AnomalyReportPersistenceRepository anomalyReportPersistenceRepository) {
        this.anomalyReportPersistenceRepository = anomalyReportPersistenceRepository;
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
}
