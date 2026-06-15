package com.spottrack.platform.reservation.interfaces.rest.transform;

import com.spottrack.platform.reservation.domain.model.commands.InitiateExpressReservation;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationStatus;
import com.spottrack.platform.reservation.interfaces.rest.resources.InitiateExpressReservationResource;

import java.awt.image.PixelGrabber;


/**
 * Converts the HTTP request body into the InitiateExpressReservation domain command.
 * No domain object construction needed here — clientId and equipmentId are plain strings.
 */
public class InitiateExpressReservationCommandFromResourceAssembler {

    public static InitiateExpressReservation toCommandFromResource(InitiateExpressReservationResource resource) {
       ReservationStatus status = ReservationStatus.valueOf(resource.status().toUpperCase());
        return new InitiateExpressReservation(resource.clientId(), resource.equipmentId(), resource.timeInterval(), status);
    }
}
