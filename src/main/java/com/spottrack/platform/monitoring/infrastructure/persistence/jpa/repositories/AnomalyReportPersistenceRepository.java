package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities.AnomalyReportPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalyReportPersistenceRepository extends JpaRepository<AnomalyReportPersistenceEntity, Long> {
}
