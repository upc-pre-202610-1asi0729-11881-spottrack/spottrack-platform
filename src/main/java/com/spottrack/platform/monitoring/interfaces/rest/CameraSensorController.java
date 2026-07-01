package com.spottrack.platform.monitoring.interfaces.rest;

import com.spottrack.platform.monitoring.application.commandServices.CameraSensorCommandService;
import com.spottrack.platform.monitoring.interfaces.rest.resources.CameraSensorResource;
import com.spottrack.platform.monitoring.interfaces.rest.resources.RegisterCameraSensorResource;
import com.spottrack.platform.monitoring.interfaces.rest.transform.CameraSensorResourceFromEntity;
import com.spottrack.platform.monitoring.interfaces.rest.transform.RegisterCameraSensorCommandFromResource;
import com.spottrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/monitoring/camera-sensors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "CameraSensors", description = "Camera sensor registration endpoints")
public class CameraSensorController {
    private final CameraSensorCommandService cameraSensorCommandService;

    public CameraSensorController(CameraSensorCommandService cameraSensorCommandService) {
        this.cameraSensorCommandService = cameraSensorCommandService;
    }

    @PostMapping
    @Operation(summary = "Register a camera sensor", description = "Registers a new camera sensor watching a specific zone.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Camera sensor registered successfully",
                    content = @Content(schema = @Schema(implementation = CameraSensorResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict - a camera sensor is already registered for this zone")
    })
    public ResponseEntity<?> registerCameraSensor(@RequestBody RegisterCameraSensorResource resource) {
        var command = RegisterCameraSensorCommandFromResource.toCommandFromResource(resource);
        var result = cameraSensorCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CameraSensorResourceFromEntity::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
