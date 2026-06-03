package com.spottrack.platform.maintenance.infrastructure.persistence.jpa;

import com.spottrack.platform.maintenance.domain.model.aggregates.TechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalTicketRepository extends JpaRepository<TechnicalTicket, TechnicalTicketId> {
}
