package com.spottrack.platform.maintenance.interfaces.rest.controllers;

import com.spottrack.platform.maintenance.application.commandServices.MaintenanceCommandService;
import com.spottrack.platform.maintenance.domain.model.aggregates.Maintenance;
import com.spottrack.platform.maintenance.domain.model.aggregates.MaintenanceJob;
import com.spottrack.platform.maintenance.domain.model.aggregates.MaintenanceLog;
import com.spottrack.platform.maintenance.domain.model.aggregates.TechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.commands.AcceptMaintenance;
import com.spottrack.platform.maintenance.domain.model.commands.AssignTechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.commands.CompleteMaintenance;
import com.spottrack.platform.maintenance.domain.model.commands.DecommissionEquipment;
import com.spottrack.platform.maintenance.domain.model.commands.RecommendEquipmentTransfer;
import com.spottrack.platform.maintenance.domain.model.commands.RegisterMaintenanceCompletion;
import com.spottrack.platform.maintenance.domain.model.commands.RequestUpdateMaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.commands.UpdateMaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceJobId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;
import com.spottrack.platform.maintenance.interfaces.rest.resources.CreateTechnicalTicketResource;
import com.spottrack.platform.maintenance.interfaces.rest.resources.DecommissionEquipmentResource;
import com.spottrack.platform.maintenance.interfaces.rest.resources.ModifyTicketStatusResource;
import com.spottrack.platform.maintenance.interfaces.rest.resources.RegisterMaintenanceCompletionResource;
import com.spottrack.platform.maintenance.interfaces.rest.resources.RequestMaintenanceResource;
import com.spottrack.platform.maintenance.interfaces.rest.resources.UpdateMaintenanceStatusResource;
import com.spottrack.platform.maintenance.interfaces.rest.transform.CreateTechnicalTicketCommandFromResourceAssembler;
import com.spottrack.platform.maintenance.interfaces.rest.transform.MaintenanceJobResourceFromEntityAssembler;
import com.spottrack.platform.maintenance.interfaces.rest.transform.MaintenanceLogResourceFromEntityAssembler;
import com.spottrack.platform.maintenance.interfaces.rest.transform.MaintenanceResourceFromEntityAssembler;
import com.spottrack.platform.maintenance.interfaces.rest.transform.RequestMaintenanceCommandFromResourceAssembler;
import com.spottrack.platform.maintenance.interfaces.rest.transform.TechnicalTicketResourceFromEntityAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/maintenance")
@Tag(name = "Maintenance")
public class MaintenanceController {

    private final MaintenanceCommandService commandService;

    public MaintenanceController(MaintenanceCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/requests")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> requestMaintenance(@RequestBody RequestMaintenanceResource resource) {
        var command = RequestMaintenanceCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Maintenance, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(MaintenanceResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Maintenance, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PostMapping("/tickets")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTechnicalTicket(@RequestBody CreateTechnicalTicketResource resource) {
        var command = CreateTechnicalTicketCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<TechnicalTicket, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(TechnicalTicketResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<TechnicalTicket, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/tickets/{ticketId}/assign/{technicianId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignTechnicalTicket(@PathVariable String ticketId, @PathVariable String technicianId) {
        var command = new AssignTechnicalTicket(new TechnicalTicketId(ticketId), technicianId);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<TechnicalTicket, ApplicationError> s ->
                    ResponseEntity.ok(TechnicalTicketResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<TechnicalTicket, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    @PatchMapping("/jobs/{jobId}/accept/{technicianId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> acceptMaintenance(@PathVariable String jobId, @PathVariable String technicianId) {
        var command = new AcceptMaintenance(new MaintenanceJobId(jobId), technicianId);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<MaintenanceJob, ApplicationError> s ->
                    ResponseEntity.ok(MaintenanceJobResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<MaintenanceJob, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    @PatchMapping("/tickets/{ticketId}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> completeMaintenance(@PathVariable String ticketId) {
        var command = new CompleteMaintenance(new TechnicalTicketId(ticketId));
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<TechnicalTicket, ApplicationError> s ->
                    ResponseEntity.ok(TechnicalTicketResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<TechnicalTicket, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    @PatchMapping("/tickets/{ticketId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> modifyTicketStatus(@PathVariable String ticketId, @RequestBody ModifyTicketStatusResource resource) {
        var command = new com.spottrack.platform.maintenance.domain.model.commands.ModifyTicketStatus(
                new TechnicalTicketId(ticketId), resource.newStatus());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<TechnicalTicket, ApplicationError> s ->
                    ResponseEntity.ok(TechnicalTicketResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<TechnicalTicket, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    @PostMapping("/tickets/{ticketId}/completion-log")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerMaintenanceCompletion(
            @PathVariable String ticketId,
            @RequestBody RegisterMaintenanceCompletionResource resource) {
        var command = new RegisterMaintenanceCompletion(
                new TechnicalTicketId(ticketId),
                new MaintenanceId(resource.maintenanceId()),
                resource.notes());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<MaintenanceLog, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(MaintenanceLogResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<MaintenanceLog, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/tickets/{ticketId}/maintenance-status/request")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> requestUpdateMaintenanceStatus(
            @PathVariable String ticketId,
            @RequestBody UpdateMaintenanceStatusResource resource) {
        var command = new RequestUpdateMaintenanceStatus(new TechnicalTicketId(ticketId), resource.newStatus());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<TechnicalTicket, ApplicationError> s ->
                    ResponseEntity.ok(TechnicalTicketResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<TechnicalTicket, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    @PatchMapping("/tickets/{ticketId}/maintenance-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMaintenanceStatus(
            @PathVariable String ticketId,
            @RequestBody UpdateMaintenanceStatusResource resource) {
        var command = new UpdateMaintenanceStatus(new TechnicalTicketId(ticketId), resource.newStatus());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<TechnicalTicket, ApplicationError> s ->
                    ResponseEntity.ok(TechnicalTicketResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<TechnicalTicket, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    @DeleteMapping("/equipment/{equipmentId}/decommission")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> decommissionEquipment(
            @PathVariable String equipmentId,
            @RequestBody DecommissionEquipmentResource resource) {
        var command = new DecommissionEquipment(equipmentId);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<String, ApplicationError> s ->
                    ResponseEntity.ok().build();
            case Result.Failure<String, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PostMapping("/equipment/{equipmentId}/transfer-recommendation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> recommendEquipmentTransfer(
            @PathVariable String equipmentId,
            @RequestBody DecommissionEquipmentResource resource) {
        var command = new RecommendEquipmentTransfer(equipmentId, resource.reason());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<String, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED).build();
            case Result.Failure<String, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }
}
