package com.spottrack.platform.analytics.application.internal.commandservices;

import com.spottrack.platform.analytics.application.commandservices.ActivityReportCommandService;
import com.spottrack.platform.analytics.domain.model.aggregates.ActivityReport;
import com.spottrack.platform.analytics.domain.model.commands.RequestActivityAnalysisCommand;
import com.spottrack.platform.analytics.domain.model.valueobjects.ActivityReportId;
import com.spottrack.platform.analytics.domain.repositories.ActivityReportRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ActivityReportCommandServiceImpl implements ActivityReportCommandService {

    private final ActivityReportRepository activityReportRepository;

    public ActivityReportCommandServiceImpl(ActivityReportRepository activityReportRepository) {
        this.activityReportRepository = activityReportRepository;
    }

    public Optional<ActivityReport> handle(RequestActivityAnalysisCommand command) {
        Long randomLongId = java.util.concurrent.ThreadLocalRandom.current().nextLong(1000, 100000);
        var activityReportId = new ActivityReportId(randomLongId);

        var activityReport = new ActivityReport(command, activityReportId);

        this.activityReportRepository.save(activityReport);

        return Optional.of(activityReport);
    }
}
