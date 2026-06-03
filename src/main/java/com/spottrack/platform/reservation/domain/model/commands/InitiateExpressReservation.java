package com.spottrack.platform.reservation.domain.model.commands;

/**
 * Command: fast-track reservation that skips the ReservationRequest flow.
 * The client walks up and immediately reserves equipment without a prior request.
 * Creates a Reservation directly in ACTIVE status.
 */
public record InitiateExpressReservation(String clientId, String equipmentId) {

    public InitiateExpressReservation {
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalArgumentException("reservation.command.expressReservation.clientId.notBlank");
        }
        if (equipmentId == null || equipmentId.isBlank()) {
            throw new IllegalArgumentException("reservation.command.expressReservation.equipmentId.notBlank");
        }
    }
}
