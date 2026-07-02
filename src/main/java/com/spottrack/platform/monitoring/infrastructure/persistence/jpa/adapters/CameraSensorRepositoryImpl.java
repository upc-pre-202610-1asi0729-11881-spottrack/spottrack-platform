package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.monitoring.domain.model.aggregates.CameraSensor;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ZoneId;
import com.spottrack.platform.monitoring.domain.repositories.CameraSensorRepository;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.CameraSensorPersistenceAssembler;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.repositories.CameraSensorPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

@Repository
public class CameraSensorRepositoryImpl implements CameraSensorRepository {
    private final CameraSensorPersistenceRepository cameraSensorPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CameraSensorRepositoryImpl(CameraSensorPersistenceRepository cameraSensorPersistenceRepository, ApplicationEventPublisher eventPublisher) {
        this.cameraSensorPersistenceRepository = cameraSensorPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean existsByZoneId(ZoneId zoneId) {
        return cameraSensorPersistenceRepository.existsByZoneId(zoneId);
    }

    @Override
    public CameraSensor save(CameraSensor cameraSensor) {
        boolean isNew = cameraSensor.getId() == null;
        var savedEntity = cameraSensorPersistenceRepository.save(CameraSensorPersistenceAssembler.toPersistenceFromDomain(cameraSensor));
        var savedDomain = CameraSensorPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            cameraSensor.onCreated();
            cameraSensor.domainEvents().forEach(eventPublisher::publishEvent);
            cameraSensor.clearDomainEvents();
        }
        return savedDomain;
    }
}
