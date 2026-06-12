package com.spottrack.platform.equipment.infrastructure.persistence.jpa;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentPersistenceRepository extends JpaRepository<Equipment, EquipmentId> {

}
