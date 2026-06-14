package com.spottrack.platform.gym.interfaces.rest.resources;

import com.spottrack.platform.gym.domain.model.valueobjects.BranchId;

public record AddZoneResource(String zoneName, int maximumOccupancy, BranchId branchId) {
}
