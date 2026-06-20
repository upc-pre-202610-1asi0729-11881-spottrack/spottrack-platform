package com.spottrack.platform.maintenance.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public enum TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    MODIFIED
}
