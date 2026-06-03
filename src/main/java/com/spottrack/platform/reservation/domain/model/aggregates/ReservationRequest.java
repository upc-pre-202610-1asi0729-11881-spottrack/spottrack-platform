package com.spottrack.platform.reservation.domain.model.aggregates;

import com.spottrack.platform.reservation.domain.model.commands.RequestAlternativeEquipment;
import com.spottrack.platform.reservation.domain.model.commands.RequestEquipmentStatusChangeToAvailable;
import com.spottrack.platform.reservation.domain.model.commands.SubmitRequestOccupyEquipment;
import com.spottrack.platform.reservation.domain.model.events.EquipmentStatusChangeToAvailableRequestedEvent;
import com.spottrack.platform.reservation.domain.model.events.RequestOccupyEquipmentSubmittedEvent;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestStatus;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate root representing a client's request to occupy equipment.
 * This is the starting point of the normal reservation flow (as opposed to express reservations).
 * It tracks which equipment was requested and notifies the Equipment bounded context to reserve it.
 *
 * Cross-context references (equipmentId, clientId) are stored as plain strings — we never
 * hold object references across bounded context boundaries.
 */
@Getter
@Entity
public class ReservationRequest extends AbstractDomainAggregateRoot<ReservationRequest> {

    @EmbeddedId
    private ReservationRequestId id;

    @Column(nullable = false)
    private String clientId;

    // String reference to Equipment bounded context — no direct object dependency
    @Column(nullable = false)
    private String equipmentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationRequestStatus status;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    protected ReservationRequest() {}

    /**
     * Creates a new request from the SubmitRequestOccupyEquipment command.
     * Status starts at SUBMITTED immediately — the act of submitting is the creation.
     */
    public ReservationRequest(SubmitRequestOccupyEquipment command) {
        this.id = new ReservationRequestId(UUID.randomUUID().toString());
        this.clientId = command.clientId();
        this.equipmentId = command.equipmentId();
        this.status = ReservationRequestStatus.SUBMITTED;
        this.requestedAt = LocalDateTime.now();
        registerDomainEvent(new RequestOccupyEquipmentSubmittedEvent(this.id.uuid(), this.equipmentId, this.clientId));
    }

    /**
     * Client requests a different piece of equipment.
     * Only allowed when the request is in SUBMITTED state.
     */
    public void requestAlternative(RequestAlternativeEquipment command) {
        if (this.status != ReservationRequestStatus.SUBMITTED) {
            throw new IllegalStateException("reservation.request.alternativeNotAllowed");
        }
        this.status = ReservationRequestStatus.ALTERNATIVE_REQUESTED;
    }

    /**
     * Signals that the equipment should be released back to AVAILABLE.
     * Called when the reservation ends or expires.
     */
    public void requestEquipmentRelease(RequestEquipmentStatusChangeToAvailable command) {
        this.status = ReservationRequestStatus.AVAILABLE_REQUESTED;
        registerDomainEvent(new EquipmentStatusChangeToAvailableRequestedEvent(this.id.uuid(), this.equipmentId));
    }
}
