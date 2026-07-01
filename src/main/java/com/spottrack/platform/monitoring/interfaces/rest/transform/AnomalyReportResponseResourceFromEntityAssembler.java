package com.spottrack.platform.monitoring.interfaces.rest.transform;

import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;
import com.spottrack.platform.monitoring.interfaces.rest.resources.AnomalyReportResponseResource;

public class AnomalyReportResponseResourceFromEntityAssembler {
    private AnomalyReportResponseResourceFromEntityAssembler() {}

    public static AnomalyReportResponseResource toResourceFromEntity(AnomalyReport entity) {
        return new AnomalyReportResponseResource(
                entity.getAnomalyReportId().uuid(),
                entity.getSessionTrackerId().uuid(),
                entity.getAnomalyType().name(),
                entity.getDescription(),
                entity.getReportedAt().toString(),
                entity.isResolved()
        );
    }
}
