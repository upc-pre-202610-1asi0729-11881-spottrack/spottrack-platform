package com.spottrack.platform.maintenance.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public enum MaintenanceStatus {
    REQUESTED,
    IN_PROGRESS,
    COMPLETED
}
