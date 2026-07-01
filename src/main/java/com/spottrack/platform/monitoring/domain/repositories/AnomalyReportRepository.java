package com.spottrack.platform.monitoring.domain.repositories;

import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;

public interface AnomalyReportRepository {
    AnomalyReport save(AnomalyReport anomalyReport);
}
