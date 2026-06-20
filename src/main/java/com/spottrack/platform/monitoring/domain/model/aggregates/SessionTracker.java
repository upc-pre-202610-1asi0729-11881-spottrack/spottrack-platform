package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.valueObjects.ReservationId;
import com.spottrack.platform.monitoring.domain.model.valueObjects.UsageActivity;

public class SessionTracker {
    UsageActivity activity;
    /**
     * id of the reservation that the session tracker is monitoring
     */
    ReservationId reservationId;
    boolean sessionIsInactive;
    boolean sessionIsActive;

    public SessionTracker(){

    }

}
