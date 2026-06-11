package com.spottrack.platform.routine.interfaces.rest;

import com.spottrack.platform.routine.application.commandservices.RoutineSessionCommandService;
import com.spottrack.platform.routine.application.queryservices.RoutineSessionQueryService;
import com.spottrack.platform.routine.domain.model.commands.CompleteRoutineCommand;
import com.spottrack.platform.routine.domain.model.commands.MarkRoutineMissedCommand;
import com.spottrack.platform.routine.domain.model.queries.GetAllRoutineSessionsByClientIdQuery;
import com.spottrack.platform.routine.domain.model.queries.GetRoutineSessionByIdQuery;
import com.spottrack.platform.routine.domain.model.valueobjects.ClientId;
import com.spottrack.platform.routine.interfaces.rest.resources.RoutineSessionResource;
import com.spottrack.platform.routine.interfaces.rest.resources.StartRoutineResource;
import com.spottrack.platform.routine.interfaces.rest.transform.RoutineSessionResourceFromEntityAssembler;
import com.spottrack.platform.routine.interfaces.rest.transform.StartRoutineCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/routine-sessions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "RoutineSessions", description = "Routine session management endpoints")
public class RoutineSessionsController {

    private final RoutineSessionCommandService routineSessionCommandService;
    private final RoutineSessionQueryService routineSessionQueryService;

    public RoutineSessionsController(
            RoutineSessionCommandService routineSessionCommandService,
            RoutineSessionQueryService routineSessionQueryService) {
        this.routineSessionCommandService = routineSessionCommandService;
        this.routineSessionQueryService = routineSessionQueryService;
    }

    @PostMapping
    @Operation(summary = "Start a routine session", description = "Starts a new routine session for a client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Routine session started successfully",
                    content = @Content(schema = @Schema(implementation = RoutineSessionResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> startRoutineSession(@Valid @RequestBody StartRoutineResource resource) {
        var command = StartRoutineCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = routineSessionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                RoutineSessionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{routineSessionId}")
    @Operation(summary = "Get routine session by ID", description = "Retrieves a routine session by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Routine session found",
                    content = @Content(schema = @Schema(implementation = RoutineSessionResource.class))),
            @ApiResponse(responseCode = "404", description = "Routine session not found")
    })
    public ResponseEntity<?> getRoutineSessionById(
            @PathVariable
            @Parameter(description = "Routine session unique identifier", example = "1", required = true)
            Long routineSessionId
    ) {
        var query = new GetRoutineSessionByIdQuery(routineSessionId);
        var session = routineSessionQueryService.handle(query);
        if (session.isEmpty()) {
            var error = ApplicationError.notFound("RoutineSession", routineSessionId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(RoutineSessionResourceFromEntityAssembler.toResourceFromEntity(session.get()));
    }

    @GetMapping
    @Operation(summary = "Get all routine sessions by client ID", description = "Retrieves all routine sessions for a specific client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Routine sessions retrieved successfully",
                    content = @Content(schema = @Schema(implementation = RoutineSessionResource.class)))
    })
    public ResponseEntity<List<RoutineSessionResource>> getAllRoutineSessionsByClientId(
            @RequestParam
            @Parameter(description = "Client identifier", example = "1", required = true)
            Long clientId
    ) {
        var query = new GetAllRoutineSessionsByClientIdQuery(new ClientId(clientId));
        var sessions = routineSessionQueryService.handle(query);
        var resources = sessions.stream()
                .map(RoutineSessionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping("/{routineSessionId}/completions")
    @Operation(summary = "Complete a routine session", description = "Marks a routine session as completed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Routine session completed successfully",
                    content = @Content(schema = @Schema(implementation = RoutineSessionResource.class))),
            @ApiResponse(responseCode = "404", description = "Routine session not found")
    })
    public ResponseEntity<?> completeRoutineSession(
            @PathVariable
            @Parameter(description = "Routine session unique identifier", example = "1", required = true)
            Long routineSessionId
    ) {
        var command = new CompleteRoutineCommand(routineSessionId);
        var result = routineSessionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                RoutineSessionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PostMapping("/{routineSessionId}/missed")
    @Operation(summary = "Mark routine session as missed", description = "Marks a routine session as missed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Routine session marked as missed",
                    content = @Content(schema = @Schema(implementation = RoutineSessionResource.class))),
            @ApiResponse(responseCode = "404", description = "Routine session not found")
    })
    public ResponseEntity<?> markRoutineSessionMissed(
            @PathVariable
            @Parameter(description = "Routine session unique identifier", example = "1", required = true)
            Long routineSessionId
    ) {
        var command = new MarkRoutineMissedCommand(routineSessionId);
        var result = routineSessionCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                RoutineSessionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
