package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.maintenance.domain.model.aggregates.TechnicalTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnicalTicketJpaRepository extends JpaRepository<TechnicalTicket, Long> {
    Optional<TechnicalTicket> findByTicketId(String ticketId);
}
