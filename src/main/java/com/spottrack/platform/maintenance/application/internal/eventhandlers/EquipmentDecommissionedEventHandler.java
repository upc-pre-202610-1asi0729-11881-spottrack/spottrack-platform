package com.spottrack.platform.maintenance.application.internal.eventhandlers;

import com.spottrack.platform.maintenance.application.commandServices.MaintenanceCommandService;
import com.spottrack.platform.maintenance.domain.model.commands.RecommendEquipmentTransfer;
import com.spottrack.platform.maintenance.domain.model.events.EquipmentDecommissionedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EquipmentDecommissionedEventHandler {

    private final MaintenanceCommandService maintenanceCommandService;

    public EquipmentDecommissionedEventHandler(MaintenanceCommandService maintenanceCommandService) {
        this.maintenanceCommandService = maintenanceCommandService;
    }

    @EventListener
    public void on(EquipmentDecommissionedEvent event) {
        maintenanceCommandService.handle(new RecommendEquipmentTransfer(
                event.equipmentId(),
                "Equipment decommissioned — transfer recommended"
        ));
    }
}
