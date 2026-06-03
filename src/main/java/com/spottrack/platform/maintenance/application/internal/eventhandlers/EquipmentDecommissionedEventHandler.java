package com.spottrack.platform.maintenance.application.internal.eventhandlers;

import com.spottrack.platform.maintenance.domain.model.events.EquipmentDecommissionedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EquipmentDecommissionedEventHandler {

    private static final Logger log = LoggerFactory.getLogger(EquipmentDecommissionedEventHandler.class);

    @EventListener
    public void on(EquipmentDecommissionedEvent event) {
        log.info("Policy fired — equipment {} decommissioned, recommending equipment transfer",
                event.equipmentId());
        // Policy: (On decommission) Recommend Equipment Transfer
    }
}
