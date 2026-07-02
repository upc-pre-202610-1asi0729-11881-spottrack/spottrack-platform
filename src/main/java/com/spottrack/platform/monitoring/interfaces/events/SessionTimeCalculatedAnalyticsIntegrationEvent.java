package com.spottrack.platform.monitoring.interfaces.events;

import java.time.LocalTime;

public record SessionTimeCalculatedAnalyticsIntegrationEvent(
        String sessionTrackerId,
        LocalTime trueActivity
) {
}
