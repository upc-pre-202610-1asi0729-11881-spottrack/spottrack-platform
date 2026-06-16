package com.spottrack.platform.gym.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.gym.domain.model.aggregates.Gym;
import com.spottrack.platform.gym.domain.model.valueobjects.GymId;
import com.spottrack.platform.gym.domain.repositories.GymRepository;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.GymPersistenceAssembler;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.GymPersistenceRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GymRepositoryImpl implements GymRepository {
    private final GymPersistenceRepository gymPersistenceRepository;

    public GymRepositoryImpl(GymPersistenceRepository gymPersistenceRepository) {
        this.gymPersistenceRepository = gymPersistenceRepository;
    }


    @Override
    public Gym save(Gym gym) {
        return null;
    }

    @Override
    public Optional<Gym> findById(GymId gymId) {
        return gymPersistenceRepository.findByGymId(gymId.uuid()).map(GymPersistenceAssembler::toDomainFromPersistence);

    }
}
