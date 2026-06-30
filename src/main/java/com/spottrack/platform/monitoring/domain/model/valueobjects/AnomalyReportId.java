package com.spottrack.platform.monitoring.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record AnomalyReportId(String uuid) {
    public AnomalyReportId {
        if (uuid == null || uuid.isBlank()) {
            throw new IllegalArgumentException("anomaly.error.anomalyReportId.notBlank");
        }
    }
}
