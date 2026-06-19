package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.entities.TechnicalTicketPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnicalTicketJpaRepository extends JpaRepository<TechnicalTicketPersistenceEntity, Long> {
    Optional<TechnicalTicketPersistenceEntity> findByTicketId(String ticketId);
}
