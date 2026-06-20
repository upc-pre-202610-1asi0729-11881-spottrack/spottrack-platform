package com.spottrack.platform.monitoring.interfaces.rest;

import com.spottrack.platform.monitoring.application.commandServices.SessionTrackerCommandService;
import com.spottrack.platform.monitoring.interfaces.rest.resources.CreateSessionTrackerResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SessionTrackerController {
    private final SessionTrackerCommandService sessionTrackerCommandService;
    public SessionTrackerController(SessionTrackerCommandService sessionTrackerCommandService){
        this.sessionTrackerCommandService = sessionTrackerCommandService;
    }

    @PostMapping("/sessionTracker/create")
    public ResponseEntity createSessionTracker(@RequestBody CreateSessionTrackerResource resource){
        try {

        }
    }

}
