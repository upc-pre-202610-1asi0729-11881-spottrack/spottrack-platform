package com.spottrack.platform.gym.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.gym.domain.repositories.EquipmentRepository;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.entities.EquipmentPersistenceEntity;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.EquipmentPersistenceRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EquipmentRepositoryImpl implements EquipmentRepository {
    private final EquipmentPersistenceRepository equipmentPersistenceRepository;

    public EquipmentRepositoryImpl(EquipmentPersistenceRepository equipmentPersistenceRepository) {
        this.equipmentPersistenceRepository = equipmentPersistenceRepository;
    }

    @Override
    public Optional<Equipment> findById(EquipmentId equipmentId) {
        return equipmentPersistenceRepository.findByEquipmentId(equipmentId.uuid()).map(EquipmentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Equipment> findByStatus(EquipmentStatus status) {
        return null;
    }

    @Override
    public List<Equipment> findByName(String equipmentName) {
        return null;
    }

    @Override
    public List<Equipment> findAll() {
        return List.of();
    }

    @Override
    public Equipment save(Equipment equipment) {
        return null;
    }
}
