package com.spottrack.platform.gym.interfaces.rest.controllers;

import com.spottrack.platform.gym.application.commandServices.GymCommandService;
import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.aggregates.Gym;
import com.spottrack.platform.gym.domain.model.entities.Branch;
import com.spottrack.platform.gym.interfaces.rest.resources.AddBranchResource;
import com.spottrack.platform.gym.interfaces.rest.resources.CreateGymResource;
import com.spottrack.platform.gym.interfaces.rest.transform.AddBranchCommandFromResourceAssembler;
import com.spottrack.platform.gym.interfaces.rest.transform.CreateGymCommandFromResourceAssembler;
import com.spottrack.platform.gym.interfaces.rest.transform.EquipmentResourceFromEntityAssembler;
import com.spottrack.platform.gym.interfaces.rest.transform.GymResourceFromEntityAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name="gyms")
public class GymController {
    GymCommandService commandService;
    public GymController(GymCommandService commandService){
        this.commandService = commandService;
    }
    @PostMapping
    public ResponseEntity<?> createGym(@RequestBody CreateGymResource resource){
        var command = CreateGymCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch(result) {
            case Result.Success<Gym, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED).body(GymResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Gym, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PostMapping("{/gyms/{gymId}/branches")
    public ResponseEntity<?> addBranch(@RequestBody AddBranchResource resource){
        var gym =
        var command = AddBranchCommandFromResourceAssembler.toCommandFromResource(resource.);
    }
}
