package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities.AnomalyReportPersistenceEntity;

public class AnomalyReportPersistenceAssembler {

    private AnomalyReportPersistenceAssembler() {}

    public static AnomalyReport toDomainFromPersistence(AnomalyReportPersistenceEntity entity) {
        return new AnomalyReport(
                entity.getId(),
                entity.getAnomalyReportId(),
                entity.getSessionTrackerId(),
                entity.getAnomalyType().name(),
                entity.getDescription(),
                entity.getReportedAt(),
                entity.isResolved()
        );
    }

    public static AnomalyReportPersistenceEntity toPersistenceFromDomain(AnomalyReport domain) {
        var entity = new AnomalyReportPersistenceEntity();
        entity.setId(domain.getPersistenceId());
        entity.setAnomalyReportId(domain.getAnomalyReportId().uuid());
        entity.setSessionTrackerId(domain.getSessionTrackerId().uuid());
        entity.setAnomalyType(domain.getAnomalyType());
        entity.setDescription(domain.getDescription());
        entity.setReportedAt(domain.getReportedAt());
        entity.setResolved(domain.isResolved());
        return entity;
    }
}
