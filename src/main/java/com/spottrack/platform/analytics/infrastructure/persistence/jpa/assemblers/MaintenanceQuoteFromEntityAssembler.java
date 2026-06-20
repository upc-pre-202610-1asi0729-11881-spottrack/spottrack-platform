package com.spottrack.platform.analytics.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.analytics.domain.model.aggregates.MaintenanceQuote;

public class MaintenanceQuoteFromEntityAssembler {
    public static MaintenanceQuote toEntityFromFields(MaintenanceQuote entity) {
        return entity;
    }
}
