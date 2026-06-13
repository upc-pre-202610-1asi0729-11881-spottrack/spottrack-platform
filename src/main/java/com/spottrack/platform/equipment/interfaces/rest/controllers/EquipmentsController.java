package com.spottrack.platform.equipment.interfaces.rest.controllers;


import com.spottrack.platform.equipment.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.equipment.application.queryservices.EquipmentQueryService;
import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.commands.MarkEquipmentOutOfService;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentById;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.equipment.interfaces.rest.resources.MarkEquipmentOutOfServiceResource;
import com.spottrack.platform.equipment.interfaces.rest.resources.RegisterEquipmentResource;
import com.spottrack.platform.equipment.interfaces.rest.resources.UpdateEquipmentStatusResource;
import com.spottrack.platform.equipment.interfaces.rest.transform.EquipmentMarkOutOfServiceFromResourceAssembler;
import com.spottrack.platform.equipment.interfaces.rest.transform.EquipmentResourceFromEntityAssembler;
import com.spottrack.platform.equipment.interfaces.rest.transform.RegisterEquipmentCommandFromResourceAssembler;
import com.spottrack.platform.equipment.interfaces.rest.transform.UpdateEquipmentStatusCommandFromResourceAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{equipmentId}")
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

    @PatchMapping("/{equipmentId}")
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

}
