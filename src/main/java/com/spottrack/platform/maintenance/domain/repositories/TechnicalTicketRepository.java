package com.spottrack.platform.maintenance.domain.repositories;

import com.spottrack.platform.maintenance.domain.model.aggregates.TechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;

import java.util.Optional;

public interface TechnicalTicketRepository {
    Optional<TechnicalTicket> findById(TechnicalTicketId id);
    TechnicalTicket save(TechnicalTicket ticket);
}
