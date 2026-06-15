package com.spottrack.platform.reservation.domain.model.aggregates;

import com.spottrack.platform.reservation.domain.model.commands.InitiateExpressReservation;
import com.spottrack.platform.reservation.domain.model.events.ReservationCancelledEvent;
import com.spottrack.platform.reservation.domain.model.events.ReservationEndedEvent;
import com.spottrack.platform.reservation.domain.model.events.ReservationTimerStartedEvent;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationStatus;
import com.spottrack.platform.reservation.domain.model.valueobjects.TimeInterval;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate root representing an active equipment reservation.
 * Can be created via express flow (no prior request) or from a submitted ReservationRequest.
 *
 * The timer tracks how long the client has to use the equipment.
 * When the timer expires, a policy triggers RequestEquipmentStatusChangeToAvailable.
 */
@Getter
@Setter
public class Reservation extends AbstractDomainAggregateRoot<Reservation> {

    private ReservationId id;

    private String clientId;

    // String reference to Equipment bounded context
    private String equipmentId;

    private ReservationStatus status;

    private LocalDateTime startedAt;

    // Set when StartReservationTimer is handled — null until the timer begins
    private LocalDateTime timerExpiry;

    private TimeInterval timeInterval;

    protected Reservation() {}

    /**
     * Express reservation: client skips the request flow and reserves equipment directly.
     * Starts in ACTIVE status with the timer not yet running.
     */
    public Reservation(InitiateExpressReservation command) {
        this.id = new ReservationId(UUID.randomUUID().toString());
        this.clientId = command.clientId().uuid();
        this.equipmentId = command.equipmentId().uuid();
        this.status = ReservationStatus.ACTIVE;
        this.startedAt = LocalDateTime.now();
        this.timeInterval = command.timeInterval(); // add this
    }

    /**
     *  Sets the timer expiry. Only makes sense when the reservation is ACTIVE.
     * durationMinutes comes from the StartReservationTimer command.
     */
    public void startTimer(int durationMinutes) {
        if (this.status != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("reservation.error.timerOnlyForActive");
        }
        this.timerExpiry = LocalDateTime.now().plusMinutes(durationMinutes);
        registerDomainEvent(new ReservationTimerStartedEvent(this.id.uuid(), this.equipmentId, this.timerExpiry));
    }

    /**
     * Client cancels the reservation. Equipment should be released after this.
     */
    public void cancel() {
        if (this.status != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("reservation.error.cancelOnlyForActive");
        }
        this.status = ReservationStatus.CANCELLED;
        registerDomainEvent(new ReservationCancelledEvent(this.id.uuid(), this.equipmentId, this.clientId));
    }

    /**
     * Client ends the reservation early. Triggers a notification to the Alerts context.
     */
    public void end() {
        if (this.status != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("reservation.error.endOnlyForActive");
        }
        this.status = ReservationStatus.ENDED;
        registerDomainEvent(new ReservationEndedEvent(this.id.uuid(), this.equipmentId, this.clientId));
    }
}
