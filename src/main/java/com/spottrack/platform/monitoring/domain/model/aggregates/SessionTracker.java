package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.commands.CreateSessionTrackerCommand;
import com.spottrack.platform.monitoring.domain.model.valueObjects.ReservationId;
import com.spottrack.platform.monitoring.domain.model.valueObjects.UsageActivity;

public class SessionTracker {
    /**
     * For security measures, we will use uuids as secondary Ids aside from the real DB Long Ids
     */
    UsageActivity usageActivity;
    /**
     * id of the reservation that the session tracker is monitoring
     */
    ReservationId reservationId;
    boolean sessionIsInactive;
    boolean sessionIsActive;

    public SessionTracker(CreateSessionTrackerCommand command){
        this.usageActivity = command.usageActivity();
        this.reservationId = command.reservationId();
        this.sessionIsInactive = command.sessionIsInactive();
        this.sessionIsActive = command.sessionIsActive();
    }



}
