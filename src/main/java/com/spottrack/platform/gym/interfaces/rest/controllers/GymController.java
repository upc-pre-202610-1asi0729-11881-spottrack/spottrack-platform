package com.spottrack.platform.gym.interfaces.rest.controllers;

import com.spottrack.platform.gym.application.commandServices.GymCommandService;
import com.spottrack.platform.gym.application.queryservices.GymQueryService;
import com.spottrack.platform.gym.domain.model.aggregates.Gym;
import com.spottrack.platform.gym.domain.model.entities.Branch;
import com.spottrack.platform.gym.domain.model.queries.GetGymById;
import com.spottrack.platform.gym.domain.model.valueobjects.GymId;
import com.spottrack.platform.gym.interfaces.rest.resources.AddBranchResource;
import com.spottrack.platform.gym.interfaces.rest.resources.CreateGymResource;
import com.spottrack.platform.gym.interfaces.rest.transform.AddBranchCommandFromResourceAssembler;
import com.spottrack.platform.gym.interfaces.rest.transform.BranchResourceFromEntityAssembler;
import com.spottrack.platform.gym.interfaces.rest.transform.CreateGymCommandFromResourceAssembler;
import com.spottrack.platform.gym.interfaces.rest.transform.GymResourceFromEntityAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gyms")
@Tag(name="gyms")
public class GymController {
    private final GymCommandService commandService;
    private final GymQueryService gymQueryService;

    public GymController(GymCommandService commandService, GymQueryService gymQueryService) {
        this.commandService = commandService;
        this.gymQueryService = gymQueryService;
    }

    @PostMapping
    public ResponseEntity<?> createGym(@RequestBody CreateGymResource resource) {
        var command = CreateGymCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Gym, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED).body(GymResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Gym, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PostMapping("/{gymId}/branches")
    public ResponseEntity<?> addBranch(@PathVariable String gymId, @RequestBody AddBranchResource resource) {
        var gym = gymQueryService.handle(new GetGymById(new GymId(gymId)));
        if (gym.isEmpty()) return ResponseEntity.notFound().build();
        var command = AddBranchCommandFromResourceAssembler.toCommandFromResource(gymId, resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Branch, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED).body(BranchResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Branch, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }
}
