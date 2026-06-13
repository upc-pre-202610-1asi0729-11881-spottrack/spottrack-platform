package com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.gym.domain.model.entities.Branch;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.entities.BranchPersistenceEntity;

public class BranchPersistenceAssembler {
    private BranchPersistenceAssembler() {

    }

    public static Branch toDomainFromPersistence(BranchPersistenceEntity entity){
        return new Branch(entity.getName(),entity.getAddress() );
    }

    public static BranchPersistenceEntity toPersistenceFromDomain(Branch entity){
        var branchEntity = new BranchPersistenceEntity();
        branchEntity.setName(entity.getName());
        branchEntity.setAddress(entity.getAddress());
        branchEntity.setBranchId(entity.getId().uuid());
        return branchEntity;
    }
}
