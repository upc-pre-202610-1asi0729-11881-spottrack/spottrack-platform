package com.spottrack.platform.monitoring.interfaces.rest.resources;

import java.time.LocalTime;

public record SessionTrackerResource(
        String sessionTrackerId, String reservationId, LocalTime continuousActivity, LocalTime seconds, boolean sessionIsActive, boolean sessionIsInactive
) {
}
