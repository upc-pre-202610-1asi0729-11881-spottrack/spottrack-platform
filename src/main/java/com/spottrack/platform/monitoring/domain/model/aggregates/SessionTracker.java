package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.valueObjects.ReservationId;
import com.spottrack.platform.monitoring.domain.model.valueObjects.UsageActivity;

public class SessionTracker {
    /**
     * For security measures, we will use uuids as secondary Ids aside from the real DB Long Ids
     */


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
