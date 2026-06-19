package com.spottrack.platform.maintenance.domain.repositories;

import com.spottrack.platform.maintenance.domain.model.aggregates.Maintenance;
import com.spottrack.platform.maintenance.domain.model.valueobjects.EquipmentId;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository {
    Optional<Maintenance> findByUuid(EquipmentId maintenanceId);
    Optional<Maintenance> findMaintenanceById(Long Id);
    List<Maintenance> findAll();
    Maintenance save(Maintenance maintenance);
}
