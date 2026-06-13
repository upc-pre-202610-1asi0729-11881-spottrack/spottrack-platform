package com.spottrack.platform.equipment.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.equipment.domain.model.aggregates.Gym;
import com.spottrack.platform.equipment.domain.model.valueobjects.GymId;
import com.spottrack.platform.equipment.domain.repositories.GymRepository;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.repositories.GymPersistenceRepository;

import java.util.Optional;

public class GymRepositoryImpl implements GymRepository {
    GymPersistenceRepository gymPersistenceRepository;


    @Override
    public Gym save(Gym gym) {
        return null;
    }

    @Override
    public Optional<Gym> findById(GymId gymId) {
        return gymPersistenceRepository.findByGymId(gymId.uuid()).map(GymPersi::toDomainFromPersistence);

    }
}
