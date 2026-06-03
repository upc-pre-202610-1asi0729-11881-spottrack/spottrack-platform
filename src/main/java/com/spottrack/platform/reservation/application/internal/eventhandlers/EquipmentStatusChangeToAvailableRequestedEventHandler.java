package com.spottrack.platform.reservation.application.internal.eventhandlers;

import com.spottrack.platform.reservation.domain.model.events.EquipmentStatusChangeToAvailableRequestedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Two policies fire from this event (as shown in the diagram):
 *
 * Policy 1: [Equipment available Requested] Notify to Equipment bounded context
 *   → tells the Equipment context to mark the equipment back as AVAILABLE.
 *
 * Policy 2: [Equipment available Requested] End Reservation
 *   → EndReservation is handled automatically by ReservationTimerStartedEventHandler
 *     when the timer expires. This handler covers the equipment-release side.
 *
 * The actual cross-context call is stubbed — in a real system this would publish
 * a message to a broker or call the Equipment service via an ACL.
 */
@Component
public class EquipmentStatusChangeToAvailableRequestedEventHandler {

    private static final Logger log = LoggerFactory.getLogger(EquipmentStatusChangeToAvailableRequestedEventHandler.class);

    @EventListener
    public void on(EquipmentStatusChangeToAvailableRequestedEvent event) {
        // TODO: notify Equipment bounded context to mark equipment AVAILABLE
        // e.g. equipmentAclService.markAsAvailable(event.equipmentId())
        log.info("Policy fired — equipment {} should be marked AVAILABLE (request {})",
                event.equipmentId(), event.requestId());
    }
}
