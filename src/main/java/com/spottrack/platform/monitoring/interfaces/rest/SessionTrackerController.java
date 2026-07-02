package com.spottrack.platform.monitoring.interfaces.rest;

import com.spottrack.platform.monitoring.application.commandServices.SessionTrackerCommandService;
import com.spottrack.platform.monitoring.application.queryServices.SessionTrackerQueryService;
import com.spottrack.platform.monitoring.domain.model.aggregates.SessionTracker;
import com.spottrack.platform.monitoring.domain.model.commands.CalculateSessionTimeCommand;
import com.spottrack.platform.monitoring.domain.model.commands.EndUsageSessionCommand;
import com.spottrack.platform.monitoring.domain.model.commands.VerifyUsageSessionCommand;
import com.spottrack.platform.monitoring.domain.model.queries.GetAllSessionTrackersQuery;
import com.spottrack.platform.monitoring.domain.model.queries.GetSessionTrackerByIdQuery;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers.SessionTrackerPersistenceAssembler;
import com.spottrack.platform.monitoring.interfaces.rest.resources.CreateSessionTrackerResource;
import com.spottrack.platform.monitoring.interfaces.rest.resources.SessionTrackerResource;
import com.spottrack.platform.monitoring.interfaces.rest.transform.CreateSessionTrackerCommandFromResource;
import com.spottrack.platform.monitoring.interfaces.rest.transform.SessionTrackerResourceFromEntity;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import com.spottrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.validator.internal.metadata.aggregated.rule.ReturnValueMayOnlyBeMarkedOnceAsCascadedPerHierarchyLine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monitoring/session-trackers")
@Tag(name="SessionTracker")
public class SessionTrackerController {
    private final SessionTrackerCommandService sessionTrackerCommandService;
    private final SessionTrackerQueryService sessionTrackerQueryService;
    public SessionTrackerController(SessionTrackerCommandService sessionTrackerCommandService, SessionTrackerQueryService sessionTrackerQueryService){
        this.sessionTrackerCommandService = sessionTrackerCommandService;
        this.sessionTrackerQueryService = sessionTrackerQueryService;
    }

    @GetMapping
    public List<SessionTrackerResource> getAllSessionTrackers() {
        return sessionTrackerQueryService.handle(new GetAllSessionTrackersQuery()).stream()
                .map(SessionTrackerResourceFromEntity::toResourceFromEntity)
                .toList();
    }

    @PostMapping("/create")
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

    @GetMapping("/{sessionTrackerId}/verify")
    public ResponseEntity verifySessionUsage(@PathVariable String sessionTrackerId) {
        var command = new VerifyUsageSessionCommand(new SessionTrackerId(sessionTrackerId));
        var result = sessionTrackerCommandService.handle(command);
        return switch (result) {
            case Result.Success<SessionTracker, ApplicationError> s ->
                ResponseEntity.ok(SessionTrackerResourceFromEntity.toResourceFromEntity(s.value()));
            case Result.Failure<SessionTracker, ApplicationError> f ->
                ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/{sessionTrackerId}/end")
    public ResponseEntity endUsageSession(@PathVariable String sessionTrackerId){
        var command = new EndUsageSessionCommand(new SessionTrackerId(sessionTrackerId));
        var result = sessionTrackerCommandService.handle(command);
         return switch(result){
             case Result.Success<SessionTracker, ApplicationError> s ->
                 ResponseEntity.ok(SessionTrackerResourceFromEntity.toResourceFromEntity(s.value()));
             case Result.Failure<SessionTracker, ApplicationError> f ->
                 ResponseEntity.badRequest().body(f.error());
         };
    }

    @GetMapping("/{sessionTrackerId}/time")
    public ResponseEntity calculateSessionTime(@PathVariable String sessionTrackerId){
            var query = new GetSessionTrackerByIdQuery(new SessionTrackerId(sessionTrackerId));
            var command = new CalculateSessionTimeCommand(query.sessionTrackerId());
            var entity = sessionTrackerQueryService.handle(query);
            var result = sessionTrackerCommandService.handle(command);
            return switch(result) {
                case Result.Success<SessionTracker, ApplicationError> s -> ResponseEntity.ok(SessionTrackerResourceFromEntity.toResourceFromEntity(s.value()));
                case Result.Failure<SessionTracker, ApplicationError>  f -> ResponseEntity.badRequest().body(f.error());
           };
    }

}

