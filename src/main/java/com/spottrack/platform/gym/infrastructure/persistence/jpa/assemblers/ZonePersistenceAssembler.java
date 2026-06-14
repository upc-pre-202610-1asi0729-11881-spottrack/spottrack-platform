package com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.gym.domain.model.entities.Zone;
import com.spottrack.platform.gym.domain.model.valueobjects.BranchId;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.entities.ZonePersistenceEntity;

public class ZonePersistenceAssembler {
    private ZonePersistenceAssembler() {

    }
    public static Zone toDomainFromPersistence(ZonePersistenceEntity entity){
        var BranchId = new BranchId(entity.getBranchId());
        var zone = new Zone(entity.getName(), entity.getMaximumOccupancy(), BranchId);
        return zone;
    }

    public static  ZonePersistenceEntity toPersistenceFromDomain(Zone entity){
        var zonePersistenceEntity = new ZonePersistenceEntity();
        zonePersistenceEntity.setName(entity.getName());
        zonePersistenceEntity.setMaximumOccupancy(entity.getMaximumOccupancy());
        zonePersistenceEntity.setBranchId(entity.getBranchId().uuid());
        return zonePersistenceEntity;
    }


}
