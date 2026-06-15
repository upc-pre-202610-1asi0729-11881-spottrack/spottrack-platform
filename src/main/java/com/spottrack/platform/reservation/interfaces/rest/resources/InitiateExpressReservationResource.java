package com.spottrack.platform.reservation.interfaces.rest.resources;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ClientId;
import com.spottrack.platform.reservation.domain.model.valueobjects.TimeInterval;

/**
 * Request body for POST /api/v1/reservations
 * clientId and equipmentId are plain string references — no domain objects cross the HTTP boundary.
 */
public record InitiateExpressReservationResource(
        String clientId,
        String equipmentId,
        String startTime,   // format: HH:mm:ss
        String endTime,     // format: HH:mm:ss
        String status
) {}