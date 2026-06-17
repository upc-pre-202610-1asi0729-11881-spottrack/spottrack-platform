package com.spottrack.platform.analytics.domain.model.commands;

public record RequestTotalUsageTimeCommand(Integer minutesActive) {
    public RequestTotalUsageTimeCommand {
        if (minutesActive == null || minutesActive < 0) {
            throw new IllegalArgumentException("Minutes active cannot be negative");
        }
    }
}