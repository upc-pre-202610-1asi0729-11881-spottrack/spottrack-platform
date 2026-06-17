package com.spottrack.platform.analytics.interfaces.rest;

import com.spottrack.platform.analytics.application.internal.commandservices.ROIProjectionCommandServiceImpl;
import com.spottrack.platform.analytics.interfaces.rest.resources.ROIProjectionResource;
import com.spottrack.platform.analytics.interfaces.rest.transform.ROIProjectionResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roi-projections")
public class ROIProjectionsController {
    private final ROIProjectionCommandServiceImpl roiProjectionCommandService;

    public ROIProjectionsController(ROIProjectionCommandServiceImpl roiProjectionCommandService) {
        this.roiProjectionCommandService = roiProjectionCommandService;
    }

    @PostMapping
    public ResponseEntity<ROIProjectionResource> createROI() {
        var roi = roiProjectionCommandService.handle(1L);
        return roi.map(value -> new ResponseEntity<>(ROIProjectionResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
