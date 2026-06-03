package com.spottrack.platform.maintenance.application.internal.eventhandlers;

import com.spottrack.platform.maintenance.domain.model.events.MaintenanceStatusUpdateRequestedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceStatusUpdateRequestedEventHandler {

    private static final Logger log = LoggerFactory.getLogger(MaintenanceStatusUpdateRequestedEventHandler.class);

    @EventListener
    public void on(MaintenanceStatusUpdateRequestedEvent event) {
        log.info("Policy fired — ticket {} requested maintenance status update to {}",
                event.ticketId(), event.requestedStatus());
        // Policy: (Maintenance Status is Completed?) Complete Maintenance — trigger completion if status is COMPLETED
    }
}
