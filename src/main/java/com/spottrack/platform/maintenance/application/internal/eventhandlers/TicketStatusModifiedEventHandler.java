package com.spottrack.platform.maintenance.application.internal.eventhandlers;

import com.spottrack.platform.maintenance.domain.model.events.TicketStatusModifiedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TicketStatusModifiedEventHandler {

    private static final Logger log = LoggerFactory.getLogger(TicketStatusModifiedEventHandler.class);

    @EventListener
    public void on(TicketStatusModifiedEvent event) {
        log.info("Policy fired — ticket {} status modified to {}, triggering complete maintenance check",
                event.ticketId(), event.newStatus());
        // Policy: (Ticket Status Completed?) Complete Maintenance — trigger completion flow if status is COMPLETED
    }
}
