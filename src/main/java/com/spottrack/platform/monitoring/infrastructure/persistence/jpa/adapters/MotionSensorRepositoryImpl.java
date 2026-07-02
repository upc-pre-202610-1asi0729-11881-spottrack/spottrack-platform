package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.monitoring.domain.model.aggregates.MotionSensor;
import com.spottrack.platform.monitoring.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.monitoring.domain.repositories.MotionSensorRepository;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.MotionSensorPersistenceAssembler;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.repositories.MotionSensorPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotionSensorRepositoryImpl implements MotionSensorRepository {
    private final MotionSensorPersistenceRepository motionSensorPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MotionSensorRepositoryImpl(MotionSensorPersistenceRepository motionSensorPersistenceRepository, ApplicationEventPublisher eventPublisher) {
        this.motionSensorPersistenceRepository = motionSensorPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean existsByEquipmentId(EquipmentId equipmentId) {
        return motionSensorPersistenceRepository.existsByEquipmentId(equipmentId);
    }

    @Override
    public List<MotionSensor> findAll() {
        return motionSensorPersistenceRepository.findAll().stream()
                .map(MotionSensorPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public MotionSensor save(MotionSensor motionSensor) {
        boolean isNew = motionSensor.getId() == null;
        var savedEntity = motionSensorPersistenceRepository.save(MotionSensorPersistenceAssembler.toPersistenceFromDomain(motionSensor));
        var savedDomain = MotionSensorPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            motionSensor.onCreated();
            motionSensor.domainEvents().forEach(eventPublisher::publishEvent);
            motionSensor.clearDomainEvents();
        }
        return savedDomain;
    }
}
