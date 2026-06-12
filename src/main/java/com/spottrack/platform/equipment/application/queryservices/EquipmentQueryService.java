package com.spottrack.platform.equipment.application.queryservices;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentStatus;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentsById;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentsByName;

import java.util.List;

public interface EquipmentQueryService {
    List<Equipment> handle(GetEquipmentsById query);
    List<Equipment> handle(GetEquipmentsByName query);
    List<Equipment> handle(GetEquipmentStatus query);
}
