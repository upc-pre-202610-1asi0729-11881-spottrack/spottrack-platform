package com.spottrack.platform.analytics.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.analytics.domain.model.aggregates.MaintenanceQuote;
import com.spottrack.platform.analytics.domain.model.valueobjects.MaintenanceQuoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaMaintenanceQuoteRepository extends JpaRepository<MaintenanceQuote, Long> {
    Optional<MaintenanceQuote> findByMaintenanceQuoteId(MaintenanceQuoteId maintenanceQuoteId);
}
