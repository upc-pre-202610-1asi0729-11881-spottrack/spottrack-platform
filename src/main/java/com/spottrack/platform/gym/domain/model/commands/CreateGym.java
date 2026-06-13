package com.spottrack.platform.gym.domain.model.commands;

import com.spottrack.platform.gym.domain.model.valueobjects.GymId;

public record CreateGym (GymId gymId, String gymName){
    public CreateGym {
        if (gymId == null) {
            throw new IllegalArgumentException("gymId must not be null");
        }
        if (gymName == null || gymName.isBlank()) {
            throw new IllegalArgumentException("gymName must not be null or blank");
        }
    }
}
