package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.commands.ReportAnomalyManuallyCommand;
import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyReportId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyType;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AnomalyReport extends AbstractDomainAggregateRoot<AnomalyReport> {

    private Long persistenceId;
    private AnomalyReportId anomalyReportId;
    private SessionTrackerId sessionTrackerId;
    private AnomalyType anomalyType;
    private String description;
    private LocalDateTime reportedAt;
    private boolean resolved;

    protected AnomalyReport() {}

    public AnomalyReport(ReportAnomalyManuallyCommand command) {
        this.anomalyReportId = new AnomalyReportId(UUID.randomUUID().toString());
        this.sessionTrackerId = command.sessionTrackerId();
        this.anomalyType = command.anomalyType();
        this.description = command.description();
        this.reportedAt = LocalDateTime.now();
        this.resolved = false;
    }

    public AnomalyReport(Long persistenceId, String anomalyReportId, String sessionTrackerId,
                         String anomalyType, String description, LocalDateTime reportedAt, boolean resolved) {
        this.persistenceId = persistenceId;
        this.anomalyReportId = new AnomalyReportId(anomalyReportId);
        this.sessionTrackerId = new SessionTrackerId(sessionTrackerId);
        this.anomalyType = AnomalyType.valueOf(anomalyType);
        this.description = description;
        this.reportedAt = reportedAt;
        this.resolved = resolved;
    }

    public void resolve() {
        this.resolved = true;
    }
}
