package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.maintenance.domain.model.aggregates.MaintenanceJob;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceJobId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceJobJpaRepository extends JpaRepository<MaintenanceJob, MaintenanceJobId> {
}
