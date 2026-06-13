package com.spottrack.platform.gym.interfaces.rest.controllers;

import com.spottrack.platform.gym.application.commandServices.GymCommandService;
import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.aggregates.Gym;
import com.spottrack.platform.gym.interfaces.rest.resources.CreateGymResource;
import com.spottrack.platform.gym.interfaces.rest.transform.CreateGymCommandFromResourceAssembler;
import com.spottrack.platform.gym.interfaces.rest.transform.EquipmentResourceFromEntityAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class GymController {
    GymCommandService commandService;
    public GymController(GymCommandService commandService){
        this.commandService = commandService;
    }
    @PostMapping
    public ResponseEntity<?> createEquipment(@RequestBody CreateGymResource resource){
        var command = CreateGymCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);

    }
}
