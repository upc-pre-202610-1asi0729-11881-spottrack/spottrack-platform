package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyReportId;
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
    private String anomalyType;
    private String description;
    private LocalDateTime reportedAt;
    private boolean resolved;

    protected AnomalyReport() {}

    public AnomalyReport(SessionTrackerId sessionTrackerId, String anomalyType, String description) {
        this.anomalyReportId = new AnomalyReportId(UUID.randomUUID().toString());
        this.sessionTrackerId = sessionTrackerId;
        this.anomalyType = anomalyType;
        this.description = description;
        this.reportedAt = LocalDateTime.now();
        this.resolved = false;
    }

    public AnomalyReport(Long persistenceId, String anomalyReportId, String sessionTrackerId,
                         String anomalyType, String description, LocalDateTime reportedAt, boolean resolved) {
        this.persistenceId = persistenceId;
        this.anomalyReportId = new AnomalyReportId(anomalyReportId);
        this.sessionTrackerId = sessionTrackerId != null ? new SessionTrackerId(sessionTrackerId) : null;
        this.anomalyType = anomalyType;
        this.description = description;
        this.reportedAt = reportedAt;
        this.resolved = resolved;
    }

    public void resolve() {
        this.resolved = true;
    }
}
