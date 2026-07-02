package com.spottrack.platform.analytics.domain.model.aggregates;

import com.spottrack.platform.analytics.domain.model.commands.RequestActivityAnalysisCommand;
import com.spottrack.platform.analytics.domain.model.events.*;
import com.spottrack.platform.analytics.domain.model.valueobjects.ActivityReportId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ActivityReport extends AbstractDomainAggregateRoot<ActivityReport> {

    @Setter
    private Long id;

    private ActivityReportId activityReportId;

    private Long totalUsageTime;
    private Long downtimeCost;
    private Double percentageComparison;

    public ActivityReport() {
    }

    public ActivityReport(RequestActivityAnalysisCommand command, ActivityReportId activityReportId) {
        this.activityReportId = activityReportId;
        this.totalUsageTime = 0L;
        this.downtimeCost = 0L;
        this.percentageComparison = 0.0;
    }

    public ActivityReport(Long id, ActivityReportId activityReportId, Long totalUsageTime, Long downtimeCost, Double percentageComparison) {
        this.id = id;
        this.activityReportId = activityReportId;
        this.totalUsageTime = totalUsageTime;
        this.downtimeCost = downtimeCost;
        this.percentageComparison = percentageComparison;
    }

    public void updateTotalUsageTime(Long totalUsageTime) {
        this.totalUsageTime = totalUsageTime;
        this.registerDomainEvent(new TotalUsageTimeRequestedEvent(this.activityReportId, totalUsageTime));
    }

    public void updateDowntimeCost(Long downtimeCost) {
        this.downtimeCost = downtimeCost;
        this.registerDomainEvent(new DowntimeRequestEvent(this.activityReportId, downtimeCost));
    }

    public void updatePercentageComparison(Double percentageComparison) {
        this.percentageComparison = percentageComparison;
        this.registerDomainEvent(new PercentageComparisonWithPreviousReportRequestedEvent(this.activityReportId, percentageComparison));
    }
}
