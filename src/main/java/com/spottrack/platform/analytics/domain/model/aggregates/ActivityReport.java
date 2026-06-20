package com.spottrack.platform.analytics.domain.model.aggregates;

import com.spottrack.platform.analytics.domain.model.commands.RequestActivityAnalysisCommand;
import com.spottrack.platform.analytics.domain.model.events.*;
import com.spottrack.platform.analytics.domain.model.valueobjects.ActivityReportId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "activity_reports")
public class ActivityReport extends AbstractDomainAggregateRoot<ActivityReport> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ActivityReportId activityReportId;

    private Long totalUsageTime;
    private Long downtimeCost;
    private Double percentageComparison;

    protected ActivityReport() {
    }

    // Constructor de negocio: Nace a partir del primer comando del Event Storming
    public ActivityReport(RequestActivityAnalysisCommand command, ActivityReportId activityReportId) {
        this.activityReportId = activityReportId;
        this.totalUsageTime = 0L;
        this.downtimeCost = 0L;
        this.percentageComparison = 0.0;
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
