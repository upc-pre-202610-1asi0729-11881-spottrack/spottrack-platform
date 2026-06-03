package com.spottrack.platform.maintenance.infrastructure.persistence.jpa;

import com.spottrack.platform.maintenance.domain.model.aggregates.MaintenanceJob;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceJobId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceJobRepository extends JpaRepository<MaintenanceJob, MaintenanceJobId> {
}
