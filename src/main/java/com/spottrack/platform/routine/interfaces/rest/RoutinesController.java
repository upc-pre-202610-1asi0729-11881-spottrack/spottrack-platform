package com.spottrack.platform.routine.interfaces.rest;

import com.spottrack.platform.routine.application.commandservices.RoutineCommandService;
import com.spottrack.platform.routine.application.queryservices.RoutineQueryService;
import com.spottrack.platform.routine.domain.model.queries.GetAllRoutinesByClientIdQuery;
import com.spottrack.platform.routine.domain.model.queries.GetRoutineByIdQuery;
import com.spottrack.platform.routine.domain.model.valueobjects.ClientId;
import com.spottrack.platform.routine.interfaces.rest.resources.AddExerciseBlockResource;
import com.spottrack.platform.routine.interfaces.rest.resources.CreateRoutineResource;
import com.spottrack.platform.routine.interfaces.rest.resources.ExerciseBlockResource;
import com.spottrack.platform.routine.interfaces.rest.resources.RoutineResource;
import com.spottrack.platform.routine.interfaces.rest.transform.AddExerciseBlockCommandFromResourceAssembler;
import com.spottrack.platform.routine.interfaces.rest.transform.CreateRoutineCommandFromResourceAssembler;
import com.spottrack.platform.routine.interfaces.rest.transform.ExerciseBlockResourceFromEntityAssembler;
import com.spottrack.platform.routine.interfaces.rest.transform.RoutineResourceFromEntityAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.spottrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/routines", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Routines", description = "Routine management endpoints")
public class RoutinesController {

    private final RoutineCommandService routineCommandService;
    private final RoutineQueryService routineQueryService;

    public RoutinesController(RoutineCommandService routineCommandService, RoutineQueryService routineQueryService) {
        this.routineCommandService = routineCommandService;
        this.routineQueryService = routineQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new routine",
            description = "Creates a new routine for a client."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Routine created successfully",
                    content = @Content(schema = @Schema(implementation = RoutineResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> createRoutine(@Valid @RequestBody CreateRoutineResource resource) {
        var command = CreateRoutineCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = routineCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                RoutineResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{routineId}")
    @Operation(
            summary = "Get routine by ID",
            description = "Retrieves a routine by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Routine found",
                    content = @Content(schema = @Schema(implementation = RoutineResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Routine not found")
    })
    public ResponseEntity<?> getRoutineById(
            @PathVariable
            @Parameter(description = "Routine unique identifier", example = "1", required = true)
            Long routineId
    ) {
        var query = new GetRoutineByIdQuery(routineId);
        var routine = routineQueryService.handle(query);
        if (routine.isEmpty()) {
            var error = ApplicationError.notFound("Routine", routineId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(RoutineResourceFromEntityAssembler.toResourceFromEntity(routine.get()));
    }

    @GetMapping
    @Operation(
            summary = "Get all routines by client ID",
            description = "Retrieves all routines belonging to a specific client."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Routines retrieved successfully",
                    content = @Content(schema = @Schema(implementation = RoutineResource.class))
            )
    })
    public ResponseEntity<List<RoutineResource>> getAllRoutinesByClientId(
            @RequestParam
            @Parameter(description = "Client identifier", example = "1", required = true)
            Long clientId
    ) {
        var query = new GetAllRoutinesByClientIdQuery(new ClientId(clientId));
        var routines = routineQueryService.handle(query);
        var resources = routines.stream()
                .map(RoutineResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping("/{routineId}/exercise-blocks")
    @Operation(
            summary = "Add an exercise block to a routine",
            description = "Adds a new exercise block to an existing routine."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Exercise block added successfully",
                    content = @Content(schema = @Schema(implementation = ExerciseBlockResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Routine not found")
    })
    public ResponseEntity<?> addExerciseBlock(
            @PathVariable
            @Parameter(description = "Routine unique identifier", example = "1", required = true)
            Long routineId,
            @Valid @RequestBody AddExerciseBlockResource resource
    ) {
        var resourceWithId = new AddExerciseBlockResource(
                routineId,
                resource.exerciseName(),
                resource.exerciseType(),
                resource.order());
        var command = AddExerciseBlockCommandFromResourceAssembler.toCommandFromResource(resourceWithId);
        var result = routineCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ExerciseBlockResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
