package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalyPersistenceRepository extends JpaRepository<AnomalyPer> {
}
