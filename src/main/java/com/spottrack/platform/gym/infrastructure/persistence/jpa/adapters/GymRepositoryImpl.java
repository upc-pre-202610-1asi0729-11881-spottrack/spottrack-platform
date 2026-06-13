package com.spottrack.platform.gym.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.gym.domain.model.aggregates.Gym;
import com.spottrack.platform.gym.domain.model.valueobjects.GymId;
import com.spottrack.platform.gym.domain.repositories.GymRepository;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.GymPersistenceAssembler;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.GymPersistenceRepository;

import java.util.Optional;

public class GymRepositoryImpl implements GymRepository {
    GymPersistenceRepository gymPersistenceRepository;


    @Override
    public Gym save(Gym gym) {
        return null;
    }

    @Override
    public Optional<Gym> findById(GymId gymId) {
        return gymPersistenceRepository.findByGymId(gymId.uuid()).map(GymPersistenceAssembler::toDomainFromPersistence);

    }
}
