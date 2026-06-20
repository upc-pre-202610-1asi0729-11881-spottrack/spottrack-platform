package com.spottrack.platform.monitoring.interfaces.rest;

import com.spottrack.platform.monitoring.application.commandServices.SessionTrackerCommandService;
import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.SessionTrackerPersistenceAssembler;
import com.spottrack.platform.monitoring.interfaces.rest.resources.CreateSessionTrackerResource;
import com.spottrack.platform.monitoring.interfaces.rest.transform.CreateSessionTrackerCommandFromResource;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.http.HttpStatus;
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
            var command = CreateSessionTrackerCommandFromResource.toCommandFromResource(resource);
            var result = sessionTrackerCommandService.handle(command);
            return switch(result){
                case Result.Success< SessionTracker, ApplicationError > s->
                    ResponseEntity.status(HttpStatus.CREATED).body(SessionTrackerPersistenceAssembler.toPersistenceFromDomain(s.value()));

                case Result.Failure<SessionTracker, ApplicationError> f->
                    ResponseEntity.badRequest().body(f.error());
            };
    }

}
