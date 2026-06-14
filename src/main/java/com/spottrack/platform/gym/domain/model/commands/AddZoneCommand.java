package com.spottrack.platform.gym.domain.model.commands;

import com.spottrack.platform.gym.domain.model.valueobjects.BranchId;

public record AddZoneCommand(String zoneName, int maximumOccupancy, BranchId branchId) {
    public AddZoneCommand {
        if (branchId == null) {
            throw new IllegalArgumentException("branchId must not be null or blank");
        }
        if (zoneName == null || zoneName.isBlank()) {
            throw new IllegalArgumentException("zoneName must not be null or blank");
        }
        if (maximumOccupancy  <= 0) {
            throw new IllegalArgumentException("maximumOccupancy cannot be 0 or a negative value");
        }
    }
}
