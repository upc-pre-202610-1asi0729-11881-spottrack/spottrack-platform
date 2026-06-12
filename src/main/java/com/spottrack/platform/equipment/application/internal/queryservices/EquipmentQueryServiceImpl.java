package com.spottrack.platform.equipment.application.internal.queryservices;

import com.spottrack.platform.equipment.application.queryservices.EquipmentQueryService;
import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentStatus;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipments;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentById;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentByName;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities.EquipmentPersistenceEntity;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.repositories.EquipmentPersistenceRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentQueryServiceImpl implements EquipmentQueryService {
    EquipmentPersistenceRepository equipmentPersistenceRepository;

    public EquipmentQueryServiceImpl(EquipmentPersistenceRepository equipmentPersistenceRepository) {
        this.equipmentPersistenceRepository = equipmentPersistenceRepository;
    }

    @Override
    public Optional<Equipment> handle(GetEquipmentById query) {
        return Optional.of();
    }

    @Override
    public Optional<Equipment> handle(GetEquipmentByName query) {
        return List.of();
    }

    @Override
    public Optional<Equipment> handle(GetEquipmentStatus query) {
        return List.of();
    }

    @Override
    public List<Equipment> handle(GetEquipments query) {
        var persistenceEquipment = equipmentPersistenceRepository.findAll();
        var persistenceToDomain = persistenceEquipment.stream().map(EquipmentPersistenceAssembler::toDomainFromPersistence);
        return persistenceToDomain.toList();
    }
}
