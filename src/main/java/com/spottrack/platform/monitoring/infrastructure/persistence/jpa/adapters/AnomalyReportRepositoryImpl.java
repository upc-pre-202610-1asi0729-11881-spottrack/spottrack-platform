package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;
import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyReportId;
import com.spottrack.platform.monitoring.domain.repositories.AnomalyReportRepository;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.AnomalyReportPersistenceAssembler;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.repositories.AnomalyReportPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AnomalyReportRepositoryImpl implements AnomalyReportRepository {

    private final AnomalyReportPersistenceRepository anomalyReportPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AnomalyReportRepositoryImpl(
            AnomalyReportPersistenceRepository anomalyReportPersistenceRepository,
            ApplicationEventPublisher eventPublisher) {
        this.anomalyReportPersistenceRepository = anomalyReportPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public AnomalyReport save(AnomalyReport anomalyReport) {
        var entity = AnomalyReportPersistenceAssembler.toPersistenceFromDomain(anomalyReport);
        var saved = anomalyReportPersistenceRepository.save(entity);
        var domain = AnomalyReportPersistenceAssembler.toDomainFromPersistence(saved);

        anomalyReport.domainEvents().forEach(eventPublisher::publishEvent);
        anomalyReport.clearDomainEvents();
        return domain;
    }

    @Override
    public Optional<AnomalyReport> findById(AnomalyReportId anomalyReportId) {
        return anomalyReportPersistenceRepository
                .findByAnomalyReportId(anomalyReportId.uuid())
                .map(AnomalyReportPersistenceAssembler::toDomainFromPersistence);
    }
}
