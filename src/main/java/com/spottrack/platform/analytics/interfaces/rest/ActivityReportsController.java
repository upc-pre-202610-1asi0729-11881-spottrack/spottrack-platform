package com.spottrack.platform.analytics.interfaces.rest;

import com.spottrack.platform.analytics.application.commandservices.ActivityReportCommandService;
import com.spottrack.platform.analytics.application.queryservices.ActivityReportQueryService;
import com.spottrack.platform.analytics.domain.model.aggregates.ActivityReport;
import com.spottrack.platform.analytics.domain.model.commands.RequestActivityAnalysisCommand;
import com.spottrack.platform.analytics.domain.model.commands.RequestDowntimeCostCommand;
import com.spottrack.platform.analytics.domain.model.commands.RequestPercentageComparisonCommand;
import com.spottrack.platform.analytics.domain.model.commands.RequestTotalUsageTimeCommand;
import com.spottrack.platform.analytics.domain.model.queries.GetAllActivityReportsQuery;
import com.spottrack.platform.analytics.interfaces.rest.resources.ActivityReportResource;
import com.spottrack.platform.analytics.interfaces.rest.transform.ActivityReportResourceFromEntityAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/activity-reports", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivityReportsController {

    private final ActivityReportCommandService activityReportCommandService;
    private final ActivityReportQueryService activityReportQueryService;

    public ActivityReportsController(ActivityReportCommandService activityReportCommandService,
                                      ActivityReportQueryService activityReportQueryService) {
        this.activityReportCommandService = activityReportCommandService;
        this.activityReportQueryService = activityReportQueryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ActivityReportResource> createActivityReport(@RequestBody RequestActivityAnalysisCommand command) {
        var activityReport = activityReportCommandService.handle(command);
        if (activityReport.isEmpty()) return ResponseEntity.badRequest().build();

        var activityReportResource = ActivityReportResourceFromEntityAssembler.toResourceFromEntity(activityReport.get());
        return new ResponseEntity<>(activityReportResource, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ActivityReportResource> getAllActivityReports() {
        return activityReportQueryService.handle(new GetAllActivityReportsQuery()).stream()
                .map(ActivityReportResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @PatchMapping("/total-usage-time")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ActivityReportResource> updateTotalUsageTime(@RequestBody RequestTotalUsageTimeCommand command) {
        var activityReport = activityReportCommandService.handle(command);
        return ResponseEntity.ok(ActivityReportResourceFromEntityAssembler.toResourceFromEntity(activityReport));
    }

    @PatchMapping("/{activityReportId}/downtime-cost")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDowntimeCost(@PathVariable Long activityReportId, @RequestBody RequestDowntimeCostCommand command) {
        var result = activityReportCommandService.handle(activityReportId, command);
        return switch (result) {
            case Result.Success<ActivityReport, ApplicationError> s ->
                    ResponseEntity.ok(ActivityReportResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<ActivityReport, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    @PatchMapping("/{activityReportId}/percentage-comparison")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePercentageComparison(@PathVariable Long activityReportId, @RequestBody RequestPercentageComparisonCommand command) {
        var result = activityReportCommandService.handle(activityReportId, command);
        return switch (result) {
            case Result.Success<ActivityReport, ApplicationError> s ->
                    ResponseEntity.ok(ActivityReportResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<ActivityReport, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }
}
