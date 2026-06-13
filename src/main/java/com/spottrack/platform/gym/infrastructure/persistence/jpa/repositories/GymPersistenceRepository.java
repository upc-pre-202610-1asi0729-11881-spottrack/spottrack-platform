package com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.gym.infrastructure.persistence.jpa.entities.GymPersistenceEntity;
import com.spottrack.platform.shared.application.result.ApplicationError;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GymPersistenceRepository extends JpaRepository<GymPersistenceEntity, Long> {
    Optional<GymPersistenceEntity> findByGymId(String gymId);
}
