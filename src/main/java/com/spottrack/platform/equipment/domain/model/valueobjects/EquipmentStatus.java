package com.spottrack.platform.equipment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public enum EquipmentStatus {
    AVAILABLE,
    OUT_OF_SERVICE,
    MAINTENANCE,
    ACTIVE,
    OCCUPIED
}
