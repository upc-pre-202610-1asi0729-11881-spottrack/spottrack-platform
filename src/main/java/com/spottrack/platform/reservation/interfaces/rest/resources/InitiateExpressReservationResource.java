package com.spottrack.platform.reservation.interfaces.rest.resources;

/**
 * Request body for POST /api/v1/reservations
 * clientId and equipmentId are plain string references — no domain objects cross the HTTP boundary.
 */
public record InitiateExpressReservationResource(String clientId, String equipmentId) {
}
