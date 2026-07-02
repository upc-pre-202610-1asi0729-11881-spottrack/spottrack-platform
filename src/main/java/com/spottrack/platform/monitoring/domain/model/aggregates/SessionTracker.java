package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.commands.CreateSessionTrackerCommand;
import com.spottrack.platform.monitoring.domain.model.events.CameraMotionCapturedEvent;
import com.spottrack.platform.monitoring.domain.model.events.MotionCapturedEvent;
import com.spottrack.platform.monitoring.domain.model.events.SessionTimeCalculatedEvent;
import com.spottrack.platform.monitoring.domain.model.events.UsageSessionEndedEvent;
import com.spottrack.platform.monitoring.domain.model.events.UsageSessionVerifiedEvent;
import com.spottrack.platform.monitoring.domain.model.valueobjects.EquipmentId;
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
     * id of the equipment being used — always present, regardless of whether
     * usage is tied to a reservation.
     */
    EquipmentId equipmentId;
    /**
     * id of the reservation that the session tracker is monitoring; null for
     * walk-up usage (equipment used without a booked reservation).
     */
    ReservationId reservationId;
    boolean sessionIsInactive;
    boolean sessionIsActive;
    LocalDateTime lastActivityAt;

    public SessionTracker(CreateSessionTrackerCommand command){
        this.sessionTrackerId = command.sessionTrackerId();
        this.equipmentId = command.equipmentId();
        this.reservationId = command.reservationId();
        this.usageActivity = command.usageActivity();
        this.sessionIsInactive = command.sessionIsInactive();
        this.sessionIsActive = command.sessionIsActive();
    }


    public SessionTracker(Long id, String sessionTrackerId, String equipmentId, String reservationId, LocalTime continousActivity, LocalTime seconds, boolean sessionIsActive, boolean sessionIsInactive, LocalDateTime lastActivityAt){
        this.id = id;
        this.sessionTrackerId = new SessionTrackerId(sessionTrackerId);
        this.equipmentId = new EquipmentId(equipmentId);
        this.reservationId = reservationId != null ? new ReservationId(reservationId) : null;
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
        registerDomainEvent(new UsageSessionEndedEvent(this.sessionTrackerId));
    }


    /**
     * This is a simple calculation substracting the inactivity time to the actual continouous activity.
     * A session that never captured any sensor motion has no lastActivityAt to measure
     * inactivity from, so it's reported as zero true activity rather than crashing.
     */
    public LocalTime calculateSessionTime() {
        if (lastActivityAt == null) {
            var trueActivity = LocalTime.MIDNIGHT;
            registerDomainEvent(new SessionTimeCalculatedEvent(this.sessionTrackerId, trueActivity));
            return trueActivity;
        }

        var activity = this.usageActivity.continuousActivity();
        var inactivity = Duration.between(lastActivityAt, LocalDateTime.now());

        var trueActivity = activity.minus(inactivity);
        registerDomainEvent(new SessionTimeCalculatedEvent(this.sessionTrackerId, trueActivity));
        return trueActivity;
    }

}
