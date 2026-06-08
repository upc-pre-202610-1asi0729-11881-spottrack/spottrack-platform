package com.spottrack.platform.routine.domain.model.valueobjects;

public record ProfileId(Long profileId) {
    public ProfileId {
        if (profileId == null) {
            throw new IllegalArgumentException("Profile Id must not be null");
        }
        if (profileId <= 0) {
            throw new IllegalArgumentException("Profile Id must be a positive number");
        }
    }
}
