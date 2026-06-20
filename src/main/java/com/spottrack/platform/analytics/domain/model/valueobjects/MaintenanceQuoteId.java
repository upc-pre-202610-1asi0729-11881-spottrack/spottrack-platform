package com.spottrack.platform.analytics.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record MaintenanceQuoteId(Long value) {
    public MaintenanceQuoteId {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("MaintenanceQuoteId must be a positive number");
        }
    }
}