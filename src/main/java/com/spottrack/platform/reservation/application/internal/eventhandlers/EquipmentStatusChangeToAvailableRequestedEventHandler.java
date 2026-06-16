package com.spottrack.platform.reservation.application.internal.eventhandlers;

import com.spottrack.platform.reservation.domain.model.events.EquipmentStatusChangeToAvailableRequestedEvent;
import com.spottrack.platform.reservation.interfaces.events.EquipmentStatusChangeToAvailableRequestedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Translates the internal {@link EquipmentStatusChangeToAvailableRequestedEvent} domain event
 * into a {@link EquipmentStatusChangeToAvailableRequestedIntegrationEvent} and re-publishes it
 * for cross-context consumers (e.g. {@code gym}).
 */
@Service
public class EquipmentStatusChangeToAvailableRequestedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public EquipmentStatusChangeToAvailableRequestedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(EquipmentStatusChangeToAvailableRequestedEvent event) {
        eventPublisher.publishEvent(new EquipmentStatusChangeToAvailableRequestedIntegrationEvent(
                event.requestId(),
                event.equipmentId(),
                "AVAILABLE"));
    }
}
