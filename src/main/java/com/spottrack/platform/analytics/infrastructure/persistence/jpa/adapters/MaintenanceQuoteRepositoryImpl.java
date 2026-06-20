package com.spottrack.platform.analytics.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.analytics.domain.model.aggregates.MaintenanceQuote;
import com.spottrack.platform.analytics.domain.model.valueobjects.MaintenanceQuoteId;
import com.spottrack.platform.analytics.domain.repositories.MaintenanceQuoteRepository;
import com.spottrack.platform.analytics.infrastructure.persistence.jpa.repositories.JpaMaintenanceQuoteRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class MaintenanceQuoteRepositoryImpl implements MaintenanceQuoteRepository {

    private final JpaMaintenanceQuoteRepository jpaMaintenanceQuoteRepository;

    public MaintenanceQuoteRepositoryImpl(JpaMaintenanceQuoteRepository jpaMaintenanceQuoteRepository) {
        this.jpaMaintenanceQuoteRepository = jpaMaintenanceQuoteRepository;
    }

    @Override
    public MaintenanceQuote save(MaintenanceQuote maintenanceQuote) {
        return this.jpaMaintenanceQuoteRepository.save(maintenanceQuote);
    }

    @Override
    public Optional<MaintenanceQuote> findByMaintenanceQuoteId(MaintenanceQuoteId maintenanceQuoteId) {
        return this.jpaMaintenanceQuoteRepository.findByMaintenanceQuoteId(maintenanceQuoteId);
    }
}
