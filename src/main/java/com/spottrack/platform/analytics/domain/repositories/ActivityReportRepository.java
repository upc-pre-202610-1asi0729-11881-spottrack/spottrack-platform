package com.spottrack.platform.analytics.domain.repositories;

import com.spottrack.platform.analytics.domain.model.aggregates.ActivityReport;
import com.spottrack.platform.analytics.domain.model.valueobjects.ActivityReportId;
import java.util.Optional;
public interface ActivityReportRepository {
    ActivityReport save(ActivityReport activityReport);
    Optional<ActivityReport> findByActivityReportId(ActivityReportId activityReportId);
}
