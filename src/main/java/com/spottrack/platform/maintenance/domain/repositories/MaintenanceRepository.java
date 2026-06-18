package com.spottrack.platform.maintenance.domain.repositories;

import com.spottrack.platform.maintenance.domain.model.aggregates.Maintenance;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository {
    Optional<Maintenance> findByUuid(String maintenanceId);
    Optional<Maintenance> findById(Long Id);
    List<Maintenance> findAll();
}
