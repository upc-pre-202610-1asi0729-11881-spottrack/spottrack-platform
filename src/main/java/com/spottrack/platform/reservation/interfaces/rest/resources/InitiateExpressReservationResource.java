package com.spottrack.platform.reservation.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Request body for POST /api/v1/reservations/reserve.
 * clientId is resolved from the JWT — it is not accepted from the request body.
 */
public record InitiateExpressReservationResource(
        String equipmentId,
        String startTime,
        String endTime,
        LocalDateTime startedAt,
        LocalDateTime timeExpiry
) {}
