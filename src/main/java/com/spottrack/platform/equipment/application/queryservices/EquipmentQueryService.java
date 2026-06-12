package com.spottrack.platform.equipment.application.queryservices;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentStatus;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipments;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentById;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentByName;

import java.util.List;
import java.util.Optional;

public interface EquipmentQueryService {
   Optional<Equipment> handle(GetEquipmentById query);
    Optional<Equipment> handle(GetEquipmentByName query);
    Optional<Equipment>handle(GetEquipmentStatus query);
    List<Equipment> handle(GetEquipments query);
}
