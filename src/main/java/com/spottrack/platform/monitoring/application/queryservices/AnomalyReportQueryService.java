package com.spottrack.platform.monitoring.application.queryservices;

import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;
import com.spottrack.platform.monitoring.domain.model.queries.GetAnomalyReportsBySessionTrackerQuery;
import com.spottrack.platform.monitoring.domain.model.queries.GetAnomalyReportsQuery;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

import java.util.List;

public interface AnomalyReportQueryService {
    Result<List<AnomalyReport>, ApplicationError> handle(GetAnomalyReportsQuery query);
    Result<List<AnomalyReport>, ApplicationError> handle(GetAnomalyReportsBySessionTrackerQuery query);
}
