package com.spottrack.platform.equipment.domain.repositories;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;

import java.util.List;
import java.util.Optional;

/**
 * Definition for the equipment repository in the domain layer
 */
public interface EquipmentRepository {
    Optional<Equipment> findById(EquipmentId equipmentId);
    List<Equipment> findByStatus(EquipmentStatus status);
    List<Equipment> findByName(String equipmentName);
    List<Equipment> findAll();
    Equipment save(Equipment equipment);

}
