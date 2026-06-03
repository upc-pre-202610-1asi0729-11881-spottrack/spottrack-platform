package com.spottrack.platform.maintenance.application.internal.eventhandlers;

import com.spottrack.platform.maintenance.domain.model.events.TicketStatusMarkedAsResolvedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TicketStatusMarkedAsResolvedEventHandler {

    private static final Logger log = LoggerFactory.getLogger(TicketStatusMarkedAsResolvedEventHandler.class);

    @EventListener
    public void on(TicketStatusMarkedAsResolvedEvent event) {
        log.info("Policy fired — ticket {} resolved, requesting equipment {} status update to available",
                event.ticketId(), event.equipmentId());
        // Policy: (Ticket Status Completed?) Request Equipment Status Updated — notify Equipment context
    }
}
