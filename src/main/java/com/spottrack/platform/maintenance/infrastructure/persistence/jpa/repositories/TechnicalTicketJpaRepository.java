package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.maintenance.domain.model.aggregates.TechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalTicketJpaRepository extends JpaRepository<TechnicalTicket, TechnicalTicketId> {
}
