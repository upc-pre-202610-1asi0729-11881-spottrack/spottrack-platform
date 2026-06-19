package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.maintenance.domain.model.aggregates.MaintenanceJob;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceJobId;
import com.spottrack.platform.maintenance.domain.repositories.MaintenanceJobRepository;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories.MaintenanceJobJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MaintenanceJobRepositoryImpl implements MaintenanceJobRepository {

    private final MaintenanceJobJpaRepository maintenanceJobJpaRepository;

    public MaintenanceJobRepositoryImpl(MaintenanceJobJpaRepository maintenanceJobJpaRepository) {
        this.maintenanceJobJpaRepository = maintenanceJobJpaRepository;
    }

    @Override
    public Optional<MaintenanceJob> findById(MaintenanceJobId id) {
        return maintenanceJobJpaRepository.findById(id);
    }

    @Override
    public MaintenanceJob save(MaintenanceJob job) {
        return maintenanceJobJpaRepository.save(job);
    }
}
