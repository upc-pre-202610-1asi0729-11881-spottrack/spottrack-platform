package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.commands.CreateSessionTrackerCommand;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ReservationId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.UsageActivity;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class SessionTracker extends AbstractDomainAggregateRoot {
    /**
     * For security measures, we will use uuids as secondary Ids aside from the real DB Long Ids
     */
    SessionTrackerId sessionTrackerId;
    UsageActivity usageActivity;
    /**
     * id of the reservation that the session tracker is monitoring
     */
    ReservationId reservationId;
    boolean sessionIsInactive;
    boolean sessionIsActive;

    public SessionTracker(CreateSessionTrackerCommand command){
        this.sessionTrackerId = command.sessionTrackerId();
        this.reservationId = command.reservationId();
        this.usageActivity = command.usageActivity();
        this.sessionIsInactive = command.sessionIsInactive();
        this.sessionIsActive = command.sessionIsActive();
    }


    public SessionTracker(String sessionTrackerId, String reservationId, LocalTime continousActivity, LocalTime seconds, boolean sessionIsActive, boolean sessionIsInactive){
        this.sessionTrackerId = new SessionTrackerId(sessionTrackerId);
        this.reservationId = new ReservationId(reservationId);
        this.usageActivity = new UsageActivity(continousActivity, seconds);
        this.sessionIsActive = sessionIsActive;
        this.sessionIsInactive = sessionIsInactive;

    }



}
