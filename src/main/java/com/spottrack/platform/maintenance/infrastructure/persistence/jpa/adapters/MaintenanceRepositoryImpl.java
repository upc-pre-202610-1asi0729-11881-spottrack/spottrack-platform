package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.maintenance.domain.model.aggregates.Maintenance;
import com.spottrack.platform.maintenance.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.maintenance.domain.repositories.MaintenanceRepository;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.assemblers.MaintenancePersistenceAssembler;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.entities.MaintenancePersistenceEntity;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories.MaintenancePersistenceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaintenanceRepositoryImpl implements MaintenanceRepository {
    MaintenancePersistenceRepository maintenancePersistenceRepository;
    public MaintenanceRepositoryImpl(MaintenancePersistenceRepository maintenancePersistenceRepository, MaintenancePersistenceAssembler maintenancePersistenceAssembler){
        this.maintenancePersistenceRepository = maintenancePersistenceRepository;
    }


    @Override
    public Optional<Maintenance> findByUuid(EquipmentId maintenanceId) {
        return Optional.empty();
    }

    @Override
    public Optional<Maintenance> findMaintenanceById(Long Id) {
        var entity = maintenancePersistenceRepository.findById(Id).map(MaintenancePersistenceAssembler::toDomainFromPersistence);
        return entity;
    }

    @Override
    public List<Maintenance> findAll() {
        List<MaintenancePersistenceEntity> persistenceList ;
        persistenceList = maintenancePersistenceRepository.findAll();
        List<Maintenance> list = new ArrayList<>();

        for (int i = 0; i < persistenceList.size(); i++) {
            list.add(MaintenancePersistenceAssembler.toDomainFromPersistence(persistenceList.get(i)));
        }

        return list;


    }
}
