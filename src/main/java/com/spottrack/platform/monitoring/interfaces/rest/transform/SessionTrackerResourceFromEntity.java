package com.spottrack.platform.monitoring.interfaces.rest.transform;

import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.interfaces.rest.resources.SessionTrackerResource;

public class SessionTrackerResourceFromEntity {
    public static SessionTrackerResource toResourceFromEntity(SessionTracker sessionTracker){
        return new SessionTrackerResource(sessionTracker.getSessionTrackerId().uuid(), sessionTracker.getReservationId().uuid(), sessionTracker.getUsageActivity().continuousActivity(),
                sessionTracker.getUsageActivity().seconds(), sessionTracker.isSessionIsActive(), sessionTracker.isSessionIsInactive());
    }
}
