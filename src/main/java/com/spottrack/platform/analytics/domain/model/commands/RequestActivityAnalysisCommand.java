package com.spottrack.platform.analytics.domain.model.commands;

public record RequestActivityAnalysisCommand(
        Integer minutesActive,
        Integer minutesInactive,
        String downtimeReason,
        Double percentageChange
) {
    public RequestActivityAnalysisCommand {
        if (minutesActive == null || minutesActive < 0) {
            throw new IllegalArgumentException("Minutes active cannot be negative");
        }
        if (minutesInactive == null || minutesInactive < 0) {
            throw new IllegalArgumentException("Minutes inactive cannot be negative");
        }
    }
}