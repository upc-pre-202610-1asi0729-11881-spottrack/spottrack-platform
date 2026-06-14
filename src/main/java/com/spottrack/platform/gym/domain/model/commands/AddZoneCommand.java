package com.spottrack.platform.gym.domain.model.commands;

public record AddZoneCommand(String zoneName, int maximumOccupancy, String branchId) {
    public AddZoneCommand {
        if (branchId == null || branchId.isBlank()) {
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
