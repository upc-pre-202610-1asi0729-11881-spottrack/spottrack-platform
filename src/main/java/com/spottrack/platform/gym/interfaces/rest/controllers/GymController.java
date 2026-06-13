package com.spottrack.platform.gym.interfaces.rest.controllers;

import com.spottrack.platform.gym.application.commandServices.GymCommandService;
import com.spottrack.platform.gym.interfaces.rest.resources.CreateGymResource;
import com.spottrack.platform.gym.interfaces.rest.transform.CreateGymCommandFromResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class GymControllers {
    GymCommandService commandService;
    public GymControllers(GymCommandService commandService){
        this.commandService = commandService;
    }
    @PostMapping
    public ResponseEntity<?> createEquipment(@RequestBody CreateGymResource resource){
        var command = CreateGymCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
    }
}
