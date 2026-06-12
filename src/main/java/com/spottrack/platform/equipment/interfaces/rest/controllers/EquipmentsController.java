package com.spottrack.platform.equipment.interfaces.rest.controllers;


import com.spottrack.platform.equipment.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.interfaces.rest.resources.RegisterEquipmentResource;
import com.spottrack.platform.equipment.interfaces.rest.transform.EquipmentResourceFromEntityAssembler;
import com.spottrack.platform.equipment.interfaces.rest.transform.RegisterEquipmentCommandFromResourceAssembler;
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

    public EquipmentsController(EquipmentCommandService commandService){
        this.commandService = commandService;
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


    @GetMapping("{id}")
    public ResponseEntity<?> getEquipmentById(EquipmentId id) {
        var equipment = GetEquipmentById
    }
}
