package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.maintenance.domain.model.aggregates.TechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;
import com.spottrack.platform.maintenance.domain.repositories.TechnicalTicketRepository;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories.TechnicalTicketJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TechnicalTicketRepositoryImpl implements TechnicalTicketRepository {

    private final TechnicalTicketJpaRepository technicalTicketJpaRepository;

    public TechnicalTicketRepositoryImpl(TechnicalTicketJpaRepository technicalTicketJpaRepository) {
        this.technicalTicketJpaRepository = technicalTicketJpaRepository;
    }

    @Override
    public Optional<TechnicalTicket> findById(TechnicalTicketId id) {
        return technicalTicketJpaRepository.findById(id);
    }

    @Override
    public TechnicalTicket save(TechnicalTicket ticket) {
        return technicalTicketJpaRepository.save(ticket);
    }
}
