package com.spottrack.platform.monitoring.interfaces.rest;

import com.spottrack.platform.monitoring.application.commandservices.AnomalyReportCommandService;
import com.spottrack.platform.monitoring.domain.model.aggregates.AnomalyReport;
import com.spottrack.platform.monitoring.interfaces.rest.resources.AnomalyReportResponseResource;
import com.spottrack.platform.monitoring.interfaces.rest.resources.ReportAnomalyResource;
import com.spottrack.platform.monitoring.interfaces.rest.transform.AnomalyReportResponseResourceFromEntityAssembler;
import com.spottrack.platform.monitoring.interfaces.rest.transform.ReportAnomalyCommandFromResourceAssembler;
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
@Tag(name = "AnomalyReports")
public class AnomalyReportController {

    private final AnomalyReportCommandService anomalyReportCommandService;

    public AnomalyReportController(AnomalyReportCommandService anomalyReportCommandService) {
        this.anomalyReportCommandService = anomalyReportCommandService;
    }

    @PostMapping("/anomaly-reports")
    public ResponseEntity<?> createAnomalyReport(@RequestBody ReportAnomalyResource resource) {
        var command = ReportAnomalyCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = anomalyReportCommandService.handle(command);
        return switch (result) {
            case Result.Success<AnomalyReport, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(AnomalyReportResponseResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<AnomalyReport, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }
}
