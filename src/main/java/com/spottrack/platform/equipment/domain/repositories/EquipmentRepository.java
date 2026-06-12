package com.spottrack.platform.equipment.domain.repositories;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;

import java.util.Optional;

/**
 * Definition for the equipment repository in the domain layer
 */
public interface EquipmentRepository {
    Optional<Equipment> findById(EquipmentId equipmentId);
    Optional<Equipment> findByStatus(EquipmentStatus status);
    Optional<Equipment> findByName(String equipmentName);
    Equipment save(Equipment equipment);
}
