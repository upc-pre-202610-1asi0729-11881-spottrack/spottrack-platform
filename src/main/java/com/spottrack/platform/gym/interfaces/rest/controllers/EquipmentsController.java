package com.spottrack.platform.gym.interfaces.rest.controllers;


import com.spottrack.platform.gym.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.gym.application.commandServices.GymCommandService;
import com.spottrack.platform.gym.application.queryservices.EquipmentQueryService;
import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.commands.CreateGym;
import com.spottrack.platform.gym.domain.model.commands.DefineMaintenanceThresholdCommand;
import com.spottrack.platform.gym.domain.model.commands.MarkEquipmentOutOfService;
import com.spottrack.platform.gym.domain.model.entities.Zone;
import com.spottrack.platform.gym.domain.model.queries.GetEquipmentById;
import com.spottrack.platform.gym.domain.model.queries.GetEquipments;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.gym.interfaces.rest.resources.*;
import com.spottrack.platform.gym.interfaces.rest.transform.*;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipments")
@Tag(name="Equipments")
public class EquipmentsController {
    private final EquipmentCommandService commandService;

    private final EquipmentQueryService equipmentQueryService;

    public EquipmentsController(EquipmentCommandService commandService, EquipmentQueryService queryService){
        this.commandService = commandService;
        this.equipmentQueryService =  queryService;
    }

    @PostMapping
    public ResponseEntity<?> registerEquipment(@RequestBody RegisterEquipmentResource resource){
        var command = RegisterEquipmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);

        return switch(result){
            case Result.Success<Equipment, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Equipment, ApplicationError> f ->
                ResponseEntity.badRequest().body(f.error());
        };
    }



    @GetMapping("/{equipmentId}")
    public ResponseEntity<?> getEquipmentById(@PathVariable String equipmentId) {
        var getEquipmentbyId = new GetEquipmentById(new EquipmentId(equipmentId));
        var equipment = equipmentQueryService.handle(getEquipmentbyId);
        if (equipment.isEmpty()) return ResponseEntity.notFound().build();
        var equipmentEntity = equipment.get();
        var equipmentResource = EquipmentResourceFromEntityAssembler.toResourceFromEntity(equipmentEntity);
        return ResponseEntity.ok(equipmentResource);
    }

    @PatchMapping("/{equipmentId}/out-of-service")
    public ResponseEntity<?> markEquipmentOutOfService(@RequestBody MarkEquipmentOutOfServiceResource resource) {
        var command = EquipmentMarkOutOfServiceFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch(result) {
            case Result.Success<Equipment, ApplicationError> s ->
                ResponseEntity.status(HttpStatus.OK).body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));

            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }




    @PatchMapping("/{equipmentId}/status")
    public ResponseEntity<?> UpdateEquipmentStatus(@RequestBody UpdateEquipmentStatusResource resource) {
        var command = UpdateEquipmentStatusCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch(result) {
            case Result.Success<Equipment, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.OK).body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));

            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/{equipmentId}/relocate")
    public ResponseEntity<?> relocateEquipment(@PathVariable String equipmentId, @RequestBody RelocateEquipmentResource resource) {
        var command = RelocateEquipmentCommandFromResourceAssembler.toCommandFromResource(equipmentId, resource);
        var result = commandService.handle(command);
        return switch(result) {
            case Result.Success<Equipment, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.OK).body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/{equipmentId}/decomission")
    public ResponseEntity<?> decomissionEquipment(@RequestBody DecomissionEquipmentResource resource) {
        var command = DecomissionEquipmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Equipment, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.OK).body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/{equipmentId}/maintenance-threshold")
    public ResponseEntity<?> defineMaintenanceThreshold(@RequestBody DefineMaintenanceThresholdResource resource){
        var command = DefineMaintenanceThresholdCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch(result) {
            case Result.Success<Equipment, ApplicationError>  s -> ResponseEntity.status(HttpStatus.OK).body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @GetMapping()
    public ResponseEntity<?> getAllEquipments(){
        var query = new GetEquipments();
        var result = equipmentQueryService.handle(query);
        return switch (result) {
            case Result.Success<List<Equipment>, ApplicationError> s ->
                    ResponseEntity.ok(s.value().stream().map(EquipmentResourceFromEntityAssembler::toResourceFromEntity).toList());
            case Result.Failure<List<Equipment>, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };

    }
}