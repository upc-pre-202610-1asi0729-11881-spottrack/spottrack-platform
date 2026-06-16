package com.spottrack.platform.reservation.interfaces.rest.transform;

import com.spottrack.platform.reservation.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.reservation.domain.model.commands.InitiateExpressReservation;
import com.spottrack.platform.reservation.domain.model.valueobjects.ClientId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationStatus;
import com.spottrack.platform.reservation.domain.model.valueobjects.TimeInterval;
import com.spottrack.platform.reservation.interfaces.rest.resources.InitiateExpressReservationResource;

import java.awt.image.PixelGrabber;
import java.sql.Time;


/**
 * Converts the HTTP request body into the InitiateExpressReservation domain command.––
 * No domain object construction needed here — clientId and equipmentId are plain strings.
 */
public class InitiateExpressReservationCommandFromResourceAssembler {

    public static InitiateExpressReservation toCommandFromResource(InitiateExpressReservationResource resource) {
       ReservationStatus status = ReservationStatus.valueOf(resource.status().toUpperCase());
       return new InitiateExpressReservation(new ClientId(resource.clientId()), new EquipmentId(resource.equipmentId()),
               new TimeInterval(Time.valueOf(resource.startTime()), Time.valueOf(resource.endTime())), status, resource.startedAt(), resource.timeExpiry());
    }
}
