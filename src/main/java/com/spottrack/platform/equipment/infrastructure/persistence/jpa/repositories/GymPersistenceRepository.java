package com.spottrack.platform.equipment.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities.GymPersistenceEntity;
import com.spottrack.platform.shared.application.result.ApplicationError;

import java.util.Optional;

public interface GymPersistenceRepository {
    Optional<GymPersistenceEntity> findByGymId(String gymId);
}
