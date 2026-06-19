package com.spottrack.platform.maintenance.application.internal.eventhandlers;

import com.spottrack.platform.maintenance.domain.model.events.TechnicalTicketCreatedEvent;
import com.spottrack.platform.maintenance.interfaces.events.EquipmentOutOfServiceIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TechnicalTicketCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public TechnicalTicketCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(TechnicalTicketCreatedEvent event) {
        eventPublisher.publishEvent(
                new EquipmentOutOfServiceIntegrationEvent(event.equipmentId(), event.ticketId(), event.maintenanceId())
        );
    }
}
