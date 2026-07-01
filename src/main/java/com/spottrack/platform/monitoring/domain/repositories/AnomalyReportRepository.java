package com.spottrack.platform.monitoring.domain.repositories;

import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;
import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyReportId;

import java.util.Optional;

public interface AnomalyReportRepository {
    AnomalyReport save(AnomalyReport anomalyReport);
    Optional<AnomalyReport> findById(AnomalyReportId anomalyReportId);
}
