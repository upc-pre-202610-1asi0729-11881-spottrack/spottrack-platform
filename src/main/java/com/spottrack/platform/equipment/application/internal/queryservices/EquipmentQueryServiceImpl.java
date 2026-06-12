package com.spottrack.platform.equipment.application.internal.queryservices;

import com.spottrack.platform.equipment.application.queryservices.EquipmentQueryService;
import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentStatus;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipments;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentById;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentByName;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
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
        var equipment = equipmentPersistenceRepository.findByEquipmentId(query.id().uuid()).map(EquipmentPersistenceAssembler::toDomainFromPersistence);
        return equipment;
    }

    @Override
    public Optional<Equipment> handle(GetEquipmentByName query) {
        var equipment = equipmentPersistenceRepository.findByEquipmentName(query.equipmentName()).map(EquipmentPersistenceAssembler::toDomainFromPersistence);
        return equipment;
    }

    @Override
    public Optional<Equipment> handle(GetEquipmentStatus query) {
        return equipmentPersistenceRepository.findByEquipmentId(query.equipmentId().uuid()).map(EquipmentPersistenceAssembler::toDomainFromPersistence);
    }


    @Override
    public List<Equipment> handle(GetEquipments query) {
        var persistenceEquipment = equipmentPersistenceRepository.findAll();
        var persistenceToDomain = persistenceEquipment.stream().map(EquipmentPersistenceAssembler::toDomainFromPersistence);
        return persistenceToDomain.toList();
    }
}
