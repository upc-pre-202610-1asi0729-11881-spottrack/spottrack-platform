package com.spottrack.platform.equipment.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities.EquipmentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentPersistenceRepository extends JpaRepository<EquipmentPersistenceEntity, Long> {
    Optional<EquipmentPersistenceEntity> findByEquipmentId(String equipmentId);
    List<EquipmentPersistenceEntity> findByEquipmentName(String equipmentName);
    List<EquipmentStatus> findByStatus(String equipmentStatus);
}
