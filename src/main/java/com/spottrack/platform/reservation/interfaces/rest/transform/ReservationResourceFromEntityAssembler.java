package com.spottrack.platform.reservation.interfaces.rest.transform;

import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.interfaces.rest.resources.ReservationResource;

/**
 * Converts the Reservation aggregate into the HTTP response resource.
 * Unwraps the ReservationId value object to a plain string for the response.
 */
public class ReservationResourceFromEntityAssembler {

    public static ReservationResource toResourceFromEntity(Reservation reservation) {
        return new ReservationResource(
                reservation.getId().uuid(),
                reservation.getClientId(),
                reservation.getEquipmentId(),
                reservation.getStatus(),
                reservation.getStartedAt(),
                reservation.getTimerExpiry()
        );
    }
}
