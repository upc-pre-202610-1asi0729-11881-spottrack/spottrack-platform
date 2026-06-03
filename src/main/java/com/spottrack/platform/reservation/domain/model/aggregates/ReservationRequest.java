package com.spottrack.platform.reservation.domain.model.aggregates;

import com.spottrack.platform.reservation.domain.model.commands.RequestAlternativeEquipment;
import com.spottrack.platform.reservation.domain.model.commands.RequestEquipmentStatusChangeToAvailable;
import com.spottrack.platform.reservation.domain.model.commands.SubmitRequestOccupyEquipment;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestStatus;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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

    private String clientId;

    // String reference to Equipment bounded context — no direct object dependency
    private String equipmentId;

    private ReservationRequestStatus status;

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
    }
}
