package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.maintenance.domain.model.aggregates.MaintenanceLog;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceLogId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceLogJpaRepository extends JpaRepository<MaintenanceLog, MaintenanceLogId> {
}
