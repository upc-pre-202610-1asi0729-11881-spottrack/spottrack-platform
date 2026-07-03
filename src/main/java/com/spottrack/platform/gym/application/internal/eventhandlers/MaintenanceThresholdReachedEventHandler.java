package com.spottrack.platform.gym.application.internal.eventhandlers;

import com.spottrack.platform.gym.domain.model.events.MaintenanceThresholdReachedEvent;
import com.spottrack.platform.gym.interfaces.events.MaintenanceThresholdAlertIntegrationEvent;
import com.spottrack.platform.gym.interfaces.events.MaintenanceThresholdReachedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Policy: [Threshold reached?] Emit Maintenance Request + Emit Alert of
 * Maintenance Threshold Reached (per the gym event-storming board).
 *
 * Two independent integration events fan out from here: one drives an
 * automatic RequestMaintenance in the maintenance context, the other is a
 * notification hook owned by another team (not consumed in this codebase).
 */
@Component
public class MaintenanceThresholdReachedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public MaintenanceThresholdReachedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(MaintenanceThresholdReachedEvent event) {
        eventPublisher.publishEvent(
                new MaintenanceThresholdReachedIntegrationEvent(event.equipmentId(), event.threshold()));

        eventPublisher.publishEvent(
                new MaintenanceThresholdAlertIntegrationEvent(event.equipmentId(), event.threshold()));
    }
}
