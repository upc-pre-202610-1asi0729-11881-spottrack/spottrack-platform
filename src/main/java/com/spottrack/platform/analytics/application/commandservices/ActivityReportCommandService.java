package com.spottrack.platform.analytics.application.commandservices;

import com.spottrack.platform.analytics.domain.model.aggregates.ActivityReport;
import com.spottrack.platform.analytics.domain.model.commands.RequestActivityAnalysisCommand;

import java.util.Optional;

public interface ActivityReportCommandService {
    Optional<ActivityReport> handle(RequestActivityAnalysisCommand command);
}
