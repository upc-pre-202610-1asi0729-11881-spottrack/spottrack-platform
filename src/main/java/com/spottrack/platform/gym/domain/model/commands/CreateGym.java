package com.spottrack.platform.gym.domain.model.commands;

import com.spottrack.platform.gym.domain.model.valueobjects.GymId;

public record CreateGym ( String gymName){
    public CreateGym {

        if (gymName == null || gymName.isBlank()) {
            throw new IllegalArgumentException("gymName must not be null or blank");
        }
    }
}
