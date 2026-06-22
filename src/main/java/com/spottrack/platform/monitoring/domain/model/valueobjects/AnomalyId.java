package com.spottrack.platform.monitoring.domain.model.valueobjects;

public record AnomalyId(Long id) {
    public AnomalyId {
        if (id == null || id == 0){
            throw new IllegalArgumentException("id must not be null or blank");
        }
    }
}
