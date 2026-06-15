package com.spottrack.platform.reservation.domain.model.commands;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ClientId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationStatus;
import com.spottrack.platform.reservation.domain.model.valueobjects.TimeInterval;

/**
 * Command: fast-track reservation that skips the ReservationRequest flow.
 * The client walks up and immediately reserves equipment without a prior request.
 * Creates a Reservation directly in ACTIVE status.
 */
public record InitiateExpressReservation(ClientId clientId, EquipmentId equipmentId, TimeInterval timeInterval, ReservationStatus reservationStatus) {

}
