package com.spottrack.platform.analytics.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.analytics.domain.model.aggregates.ActivityReport;
import com.spottrack.platform.analytics.domain.model.valueobjects.ActivityReportId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaActivityReportRepository extends JpaRepository<ActivityReport, Long> {
    Optional<ActivityReport> findByActivityReportId(ActivityReportId activityReportId);
}
