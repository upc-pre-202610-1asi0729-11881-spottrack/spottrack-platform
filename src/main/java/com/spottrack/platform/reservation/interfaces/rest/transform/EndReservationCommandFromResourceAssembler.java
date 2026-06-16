package com.spottrack.platform.reservation.interfaces.rest.transform;

import com.spottrack.platform.reservation.domain.model.commands.EndReservation;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationId;
import com.spottrack.platform.reservation.interfaces.rest.resources.EndReservationCommandResource;

public class EndReservationCommandFromResourceAssembler {
    public static EndReservation toCommandFromResource(EndReservationCommandResource resource) {
        var command = new EndReservation(new ReservationId(resource.reservationId()));
        return command;
    }
}
