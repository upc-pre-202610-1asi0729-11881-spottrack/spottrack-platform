package com.spottrack.platform.analytics.interfaces.rest;

import com.spottrack.platform.analytics.application.commandservices.ActivityReportCommandService;
import com.spottrack.platform.analytics.domain.model.commands.RequestActivityAnalysisCommand;
import com.spottrack.platform.analytics.interfaces.rest.resources.ActivityReportResource;
import com.spottrack.platform.analytics.interfaces.rest.transform.ActivityReportResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/activity-reports", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivityReportsController {

    private final ActivityReportCommandService activityReportCommandService;

    public ActivityReportsController(ActivityReportCommandService activityReportCommandService) {
        this.activityReportCommandService = activityReportCommandService;
    }

    @PostMapping
    public ResponseEntity<ActivityReportResource> createActivityReport(@RequestBody RequestActivityAnalysisCommand command) {
        var activityReport = activityReportCommandService.handle(command);
        if (activityReport.isEmpty()) return ResponseEntity.badRequest().build();

        var activityReportResource = ActivityReportResourceFromEntityAssembler.toResourceFromEntity(activityReport.get());
        return new ResponseEntity<>(activityReportResource, HttpStatus.CREATED);
    }
}
