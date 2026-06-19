package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.maintenance.domain.model.aggregates.Maintenance;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.entities.MaintenancePersistenceEntity;

public class MaintenancePersistenceAssembler {
    public static Maintenance toDomainFromPersistence(MaintenancePersistenceEntity entity){
        return new Maintenance(entity.getMaintenanceId(), entity.getEquipmentId(), entity.getRequestedBy(), entity.getDescription(),
                entity.getStatus());
    }
}
