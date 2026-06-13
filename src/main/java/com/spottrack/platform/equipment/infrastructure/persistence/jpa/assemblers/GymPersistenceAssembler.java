package com.spottrack.platform.equipment.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.equipment.domain.model.aggregates.Gym;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities.BranchPersistenceEntity;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities.GymPersistenceEntity;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.repositories.GymPersistenceRepository;

public class GymPersistenceAssembler {
    private GymPersistenceAssembler(){

    }

    public static Gym toDomainFromPersistence(GymPersistenceEntity entity){
        var gym = new Gym(entity.getName());
        return gym;
    }

    public static GymPersistenceEntity toPersistenceFromDomain(Gym entity){
        var gymPersistenceEntity = new GymPersistenceEntity();

        gymPersistenceEntity.setGymId(entity.getId().uuid());
        gymPersistenceEntity.setName(entity.getName());
        return gymPersistenceEntity;
    }
}
