package com.spottrack.platform.maintenance.domain.repositories;

import com.spottrack.platform.maintenance.domain.model.aggregates.MaintenanceLog;

public interface MaintenanceLogRepository {
    MaintenanceLog save(MaintenanceLog log);
}
