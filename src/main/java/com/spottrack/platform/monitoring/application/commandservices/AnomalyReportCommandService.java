package com.spottrack.platform.monitoring.application.commandservices;

import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;
import com.spottrack.platform.monitoring.domain.model.commands.ReportAnomalyManuallyCommand;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

public interface AnomalyReportCommandService {
    Result<AnomalyReport, ApplicationError> handle(ReportAnomalyManuallyCommand command);
}
