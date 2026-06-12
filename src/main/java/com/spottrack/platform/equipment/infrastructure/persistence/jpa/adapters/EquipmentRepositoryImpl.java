package com.spottrack.platform.equipment.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.equipment.domain.repositories.EquipmentRepository;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.repositories.EquipmentPersistenceRepository;

import java.util.Optional;

public class EquipmentRepositoryImpl implements EquipmentRepository {
    EquipmentPersistenceRepository equipmentPersistenceRepository;

    @Override
    public Optional<Equipment> findById(EquipmentId equipmentId) {
        return equipmentPersistenceRepository.findByEquipmentId(equipmentId.uuid()).map(EquipmentPersi);
    }

    @Override
    public Optional<Equipment> findByStatus(EquipmentStatus status) {
        return Optional.empty();
    }

    @Override
    public Optional<Equipment> findByName(String equipmentName) {
        return Optional.empty();
    }

    @Override
    public Equipment save(Equipment equipment) {
        return null;
    }
}
