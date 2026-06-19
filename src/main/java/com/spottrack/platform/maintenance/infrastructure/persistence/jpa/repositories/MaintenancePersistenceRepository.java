package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.maintenance.domain.model.aggregates.Maintenance;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceId;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.entities.MaintenancePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaintenancePersistenceRepository extends JpaRepository<MaintenancePersistenceEntity, Long> {
    Optional<MaintenancePersistenceEntity> findByMaintenanceId(String id);
    Optional<MaintenancePersistenceEntity> findByMaintenanceStatus(String status);
    List<MaintenancePersistenceEntity> findAll();
}
