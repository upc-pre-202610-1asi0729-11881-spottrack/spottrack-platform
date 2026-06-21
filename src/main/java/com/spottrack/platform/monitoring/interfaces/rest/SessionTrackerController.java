package com.spottrack.platform.monitoring.interfaces.rest;

import com.spottrack.platform.monitoring.application.commandServices.SessionTrackerCommandService;
import com.spottrack.platform.monitoring.application.queryServices.SessionTrackerQueryService;
import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.domain.model.commands.VerifyUsageSessionCommand;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.SessionTrackerPersistenceAssembler;
import com.spottrack.platform.monitoring.interfaces.rest.resources.CreateSessionTrackerResource;
import com.spottrack.platform.monitoring.interfaces.rest.transform.CreateSessionTrackerCommandFromResource;
import com.spottrack.platform.monitoring.interfaces.rest.transform.SessionTrackerResourceFromEntity;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import com.spottrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class SessionTrackerController {
    private final SessionTrackerCommandService sessionTrackerCommandService;
    private final SessionTrackerQueryService sessionTrackerQueryService;
    public SessionTrackerController(SessionTrackerCommandService sessionTrackerCommandService, SessionTrackerQueryService sessionTrackerQueryService){
        this.sessionTrackerCommandService = sessionTrackerCommandService;
        this.sessionTrackerQueryService = sessionTrackerQueryService;
    }

    @PostMapping("/sessionTracker/create")
    public ResponseEntity createSessionTracker(@RequestBody CreateSessionTrackerResource resource){
            var command = CreateSessionTrackerCommandFromResource.toCommandFromResource(resource);
            var result = sessionTrackerCommandService.handle(command);
            return switch(result){
                case Result.Success< SessionTracker, ApplicationError > s->
                    ResponseEntity.status(HttpStatus.CREATED).body(SessionTrackerPersistenceAssembler.toPersistenceFromDomain(s.value()));

                case Result.Failure<SessionTracker, ApplicationError> f->
                    ResponseEntity.badRequest().body(f.error());
            };
    }

    @PatchMapping("/sessionTracker/{sessionTrackerId}/verify")
    public ResponseEntity verifySessionUsage(@PathVariable String sessionTrackerId) {
        var command = new VerifyUsageSessionCommand(sessionTrackerId);
        var result = sessionTrackerCommandService.handle(command);
        return switch (result) {
            case Result.Success<SessionTracker, ApplicationError> s ->
                ResponseEntity.ok(SessionTrackerResourceFromEntity.toResourceFromEntity(s.value()));
            case Result.Failure<SessionTracker, ApplicationError> f ->
                ResponseEntity.badRequest().body(f.error());
        };
    }
}

