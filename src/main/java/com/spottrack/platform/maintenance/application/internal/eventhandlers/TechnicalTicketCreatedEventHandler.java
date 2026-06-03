package com.spottrack.platform.maintenance.application.internal.eventhandlers;

import com.spottrack.platform.maintenance.domain.model.events.TechnicalTicketCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TechnicalTicketCreatedEventHandler {

    private static final Logger log = LoggerFactory.getLogger(TechnicalTicketCreatedEventHandler.class);

    @EventListener
    public void on(TechnicalTicketCreatedEvent event) {
        log.info("Policy fired — ticket {} created for maintenance {}, equipment {} marked out of service",
                event.ticketId(), event.maintenanceId(), event.equipmentId());
        // Policy: (Technical Ticket Created?) Mark Equipment Out Of Service — notify Equipment context
    }
}
