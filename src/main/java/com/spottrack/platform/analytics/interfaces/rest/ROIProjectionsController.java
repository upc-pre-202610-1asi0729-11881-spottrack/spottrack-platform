package com.spottrack.platform.analytics.interfaces.rest;

import com.spottrack.platform.analytics.application.commandservices.ROIProjectionCommandService;
import com.spottrack.platform.analytics.domain.model.commands.RequestRoiCommand;
import com.spottrack.platform.analytics.interfaces.rest.resources.ROIProjectionResource;
import com.spottrack.platform.analytics.interfaces.rest.transform.ROIProjectionResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/roi-projections", produces = MediaType.APPLICATION_JSON_VALUE)
public class ROIProjectionsController {
    private final ROIProjectionCommandService roiProjectionCommandService;

    public ROIProjectionsController(ROIProjectionCommandService roiProjectionCommandService) {
        this.roiProjectionCommandService = roiProjectionCommandService;
    }

    @PostMapping
    public ResponseEntity<ROIProjectionResource> createROI(@RequestBody RequestRoiCommand command) {
        var roi = roiProjectionCommandService.handle(command);
        return roi.map(value -> new ResponseEntity<>(ROIProjectionResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
