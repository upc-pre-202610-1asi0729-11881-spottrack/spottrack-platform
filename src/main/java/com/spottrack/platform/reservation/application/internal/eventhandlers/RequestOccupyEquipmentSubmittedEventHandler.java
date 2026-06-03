package com.spottrack.platform.reservation.application.internal.eventhandlers;

import com.spottrack.platform.reservation.domain.model.events.RequestOccupyEquipmentSubmittedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Policy: [Equipment Status = Reserved] Notify to Equipment bounded context
 *
 * Fires when a ReservationRequest is submitted. The equipment context must be
 * notified to mark the requested equipment as RESERVED so no one else can take it.
 *
 * The actual cross-context call is stubbed — in a real system this would publish
 * a message to a broker (Kafka, RabbitMQ) or call the Equipment service via HTTP/ACL.
 */
@Component
public class RequestOccupyEquipmentSubmittedEventHandler {

    private static final Logger log = LoggerFactory.getLogger(RequestOccupyEquipmentSubmittedEventHandler.class);

    @EventListener
    public void on(RequestOccupyEquipmentSubmittedEvent event) {
        // TODO: notify Equipment bounded context to mark equipment RESERVED
        // e.g. equipmentAclService.markAsReserved(event.equipmentId())
        log.info("Policy fired — equipment {} should be marked RESERVED for request {}",
                event.equipmentId(), event.requestId());
    }
}
