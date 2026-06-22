package com.spottrack.platform.monitoring.application.internal.eventhandler;

import com.spottrack.platform.monitoring.application.commandServices.SessionTrackerCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UsageSessionVerifiedEventHandler {
    SessionTrackerCommandService sessionTrackerCommandService;

    public UsageSessionVerifiedEventHandler(SessionTrackerCommandService sessionTrackerCommandService){
        this.sessionTrackerCommandService =  sessionTrackerCommandService;
    }


    @EventListener
    public void on()
}
