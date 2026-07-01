package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.monitoring.domain.model.aggregates.Anomaly;
import com.spottrack.platform.monitoring.domain.repositories.AnomalyRepository;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.AnomalyPersistenceAssembler;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.repositories.AnomalyPersistenceRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AnomalyRepositoryImpl implements AnomalyRepository {
    private final AnomalyPersistenceRepository anomalyPersistenceRepository;
    public AnomalyRepositoryImpl(AnomalyPersistenceRepository anomalyPersistenceRepository){
        this.anomalyPersistenceRepository = anomalyPersistenceRepository;
    }

    @Override
    public Anomaly save(Anomaly anomaly) {
        var entity = AnomalyPersistenceAssembler.toPersistenceFromDomain(anomaly);
        var saved = anomalyPersistenceRepository.save(entity);
        return AnomalyPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
