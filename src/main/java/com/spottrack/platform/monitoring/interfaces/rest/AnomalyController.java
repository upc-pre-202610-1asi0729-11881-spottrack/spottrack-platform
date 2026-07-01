package com.spottrack.platform.monitoring.interfaces.rest;

import com.spottrack.platform.monitoring.application.commandServices.AnomalyCommandService;
import com.spottrack.platform.monitoring.interfaces.rest.resources.AnomalyResource;
import com.spottrack.platform.monitoring.interfaces.rest.resources.ReportAnomalyCommandResource;
import com.spottrack.platform.monitoring.interfaces.rest.transform.AnomalyResourceFromEntity;
import com.spottrack.platform.monitoring.interfaces.rest.transform.ReportAnomalyCommandFromResource;
import com.spottrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/anomalies")
@Tag(name="Anomalies")
public class AnomalyController {
    private final AnomalyCommandService anomalyCommandService;
    public AnomalyController(AnomalyCommandService anomalyCommandService){
        this.anomalyCommandService = anomalyCommandService;
    }

    @PostMapping
    public ResponseEntity<?> reportAnomaly(ReportAnomalyCommandResource resource){
        var command = ReportAnomalyCommandFromResource.toCommandFromResource(resource);
        var result = anomalyCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AnomalyResourceFromEntity::toResourceFromEntity,
                HttpStatus.CREATED

        );
    }
}
