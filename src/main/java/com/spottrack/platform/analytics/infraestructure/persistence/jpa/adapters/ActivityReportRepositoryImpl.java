package com.spottrack.platform.analytics.infraestructure.persistence.jpa.adapters;

import com.spottrack.platform.analytics.domain.model.aggregates.ActivityReport;
import com.spottrack.platform.analytics.domain.model.valueobjects.ActivityReportId;
import com.spottrack.platform.analytics.domain.repositories.ActivityReportRepository;
import com.spottrack.platform.analytics.infrastructure.persistence.jpa.repositories.JpaActivityReportRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class ActivityReportRepositoryImpl implements ActivityReportRepository {

    private final JpaActivityReportRepository jpaActivityReportRepository;

    public ActivityReportRepositoryImpl(JpaActivityReportRepository jpaActivityReportRepository) {
        this.jpaActivityReportRepository = jpaActivityReportRepository;
    }

    @Override
    public ActivityReport save(ActivityReport activityReport) {
        return jpaActivityReportRepository.save(activityReport);
    }

    @Override
    public Optional<ActivityReport> findByActivityReportId(ActivityReportId activityReportId) {
        return jpaActivityReportRepository.findByActivityReportId(activityReportId);
    }
}
