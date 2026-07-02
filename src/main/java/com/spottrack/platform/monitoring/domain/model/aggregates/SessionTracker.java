package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.commands.CreateSessionTrackerCommand;
import com.spottrack.platform.monitoring.domain.model.events.CameraMotionCapturedEvent;
import com.spottrack.platform.monitoring.domain.model.events.MotionCapturedEvent;
import com.spottrack.platform.monitoring.domain.model.events.SessionTimeCalculatedEvent;
import com.spottrack.platform.monitoring.domain.model.events.UsageSessionVerifiedEvent;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ReservationId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.UsageActivity;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class SessionTracker extends AbstractDomainAggregateRoot {
    /**
     * For security measures, we will use uuids as secondary Ids aside from the real DB Long Ids
     */
    Long id;
    SessionTrackerId sessionTrackerId;
    UsageActivity usageActivity;
    /**
     * id of the reservation that the session tracker is monitoring
     */
    ReservationId reservationId;
    boolean sessionIsInactive;
    boolean sessionIsActive;
    LocalDateTime lastActivityAt;

    public SessionTracker(CreateSessionTrackerCommand command){
        this.sessionTrackerId = command.sessionTrackerId();
        this.reservationId = command.reservationId();
        this.usageActivity = command.usageActivity();
        this.sessionIsInactive = command.sessionIsInactive();
        this.sessionIsActive = command.sessionIsActive();
    }


    public SessionTracker(Long id, String sessionTrackerId, String reservationId, LocalTime continousActivity, LocalTime seconds, boolean sessionIsActive, boolean sessionIsInactive, LocalDateTime lastActivityAt){
        this.id = id;
        this.sessionTrackerId = new SessionTrackerId(sessionTrackerId);
        this.reservationId = new ReservationId(reservationId);
        this.usageActivity = new UsageActivity(continousActivity, seconds);
        this.sessionIsActive = sessionIsActive;
        this.sessionIsInactive = sessionIsInactive;
        this.lastActivityAt = lastActivityAt;
    }

    public void recordActivity() {
        this.lastActivityAt = LocalDateTime.now();
    }

    /**
     * Captured motion keeps the session's activity clock fresh, which is what
     * prevents the inactivity policy from ending the session prematurely.
     */
    public void captureCameraMotion(boolean detected) {
        if (detected) {
            recordActivity();
        }
        registerDomainEvent(new CameraMotionCapturedEvent(this.sessionTrackerId, detected));
    }

    public void captureMotionSensorReading(boolean detected) {
        if (detected) {
            recordActivity();
        }
        registerDomainEvent(new MotionCapturedEvent(this.sessionTrackerId, detected));
    }

    public boolean verifyUsageSession() {
        sessionIsInactive = lastActivityAt != null && Duration.between(lastActivityAt, LocalDateTime.now()).toMinutes() >= 3;
        registerDomainEvent(new UsageSessionVerifiedEvent(this.sessionTrackerId));
        return sessionIsInactive;
    }

    public void endSession(){
        this.sessionIsActive= false;
        this.sessionIsInactive = true;
    }


    /**
     * This is a simple calculation substracting the inactivity time to the actual continouous activity
     */
    public LocalTime calculateSessionTime() {
        var activity = this.usageActivity.seconds();
        var inactivity = Duration.between(lastActivityAt, this.usageActivity.seconds());

        var trueActivity = activity.minus(inactivity);
        registerDomainEvent(new SessionTimeCalculatedEvent(this.sessionTrackerId, trueActivity));
        return trueActivity;
    }

}
