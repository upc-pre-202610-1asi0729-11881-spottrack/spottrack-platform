package com.spottrack.platform.reservation.interfaces.rest.resources;

import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationStatus;

import java.time.LocalDateTime;

/**
 * Response body for Reservation endpoints.
 * timerExpiry is null until StartReservationTimer is called.
 */
public record ReservationResource(
        String id,
        String clientId,
        String equipmentId,
        ReservationStatus status,
        LocalDateTime startedAt,
        LocalDateTime timerExpiry
) {
}
