package com.spottrack.platform.reservation.interfaces.rest.resources;

/**
 * Request body for POST /api/v1/reservation-requests
 */
public record SubmitRequestOccupyEquipmentResource(Long clientId, String equipmentId) {
}
