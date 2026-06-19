package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.maintenance.domain.model.aggregates.MaintenanceLog;
import com.spottrack.platform.maintenance.domain.repositories.MaintenanceLogRepository;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceLogJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MaintenanceLogRepositoryImpl implements MaintenanceLogRepository {

    private final MaintenanceLogJpaRepository maintenanceLogJpaRepository;

    public MaintenanceLogRepositoryImpl(MaintenanceLogJpaRepository maintenanceLogJpaRepository) {
        this.maintenanceLogJpaRepository = maintenanceLogJpaRepository;
    }

    @Override
    public MaintenanceLog save(MaintenanceLog log) {
        return maintenanceLogJpaRepository.save(log);
    }
}
