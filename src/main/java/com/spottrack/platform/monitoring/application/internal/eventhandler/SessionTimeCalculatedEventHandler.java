package com.spottrack.platform.monitoring.application.internal.eventhandler;

import com.spottrack.platform.monitoring.domain.model.events.SessionTimeCalculatedEvent;
import com.spottrack.platform.monitoring.interfaces.events.SessionTimeCalculatedAnalyticsIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Implements the "(Session Time Calculated Successfully) Send Notification To
 * Analytics" policy from the event storming. This context's responsibility ends
 * at publishing the integration event below; recording/aggregating the analytics
 * data is not this bounded context's job. Whoever owns that responsibility should
 * add a listener for {@link SessionTimeCalculatedAnalyticsIntegrationEvent} (e.g.
 * in the Analytics bounded context) and implement the real ingestion there.
 */
@Service
@Slf4j
public class SessionTimeCalculatedEventHandler {
    private final ApplicationEventPublisher eventPublisher;

    public SessionTimeCalculatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(SessionTimeCalculatedEvent event) {
        log.info("Session time calculated for session tracker {}; publishing integration event for Analytics.",
                event.sessionTrackerId().uuid());
        eventPublisher.publishEvent(new SessionTimeCalculatedAnalyticsIntegrationEvent(
                event.sessionTrackerId().uuid(),
                event.trueActivity()
        ));
    }
}
