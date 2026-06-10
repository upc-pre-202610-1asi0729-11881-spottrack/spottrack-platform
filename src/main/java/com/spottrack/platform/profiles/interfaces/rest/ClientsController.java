package com.spottrack.platform.profiles.interfaces.rest;

import com.spottrack.platform.profiles.application.commandservices.ClientCommandService;
import com.spottrack.platform.profiles.application.queryservices.ClientQueryService;
import com.spottrack.platform.profiles.domain.model.queries.GetClientByIdQuery;
import com.spottrack.platform.profiles.domain.model.valueobjects.ClientId;
import com.spottrack.platform.profiles.interfaces.rest.resources.ClientResource;
import com.spottrack.platform.profiles.interfaces.rest.resources.CreateClientResource;
import com.spottrack.platform.profiles.interfaces.rest.transform.ClientResourceFromEntityAssembler;
import com.spottrack.platform.profiles.interfaces.rest.transform.CreateClientCommandFromResourceAssembler;
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

@RestController
@RequestMapping(value = "/api/v1/profiles/clients", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Clients", description = "Client profile management endpoints")
public class ClientsController {

    private final ClientCommandService clientCommandService;
    private final ClientQueryService clientQueryService;

    public ClientsController(ClientCommandService clientCommandService, ClientQueryService clientQueryService) {
        this.clientCommandService = clientCommandService;
        this.clientQueryService = clientQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new client",
            description = "Creates a new client profile with personal and contact information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Client created successfully",
                    content = @Content(schema = @Schema(implementation = ClientResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict - client already exists")
    })
    public ResponseEntity<?> createClient(@Valid @RequestBody CreateClientResource resource) {
        var command = CreateClientCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = clientCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ClientResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{clientId}")
    @Operation(
            summary = "Get client by ID",
            description = "Retrieves a client profile by unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Client found",
                    content = @Content(schema = @Schema(implementation = ClientResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<?> getClientById(
            @PathVariable
            @Parameter(description = "Client unique identifier", example = "1", required = true)
            Long clientId
    ) {
        var query = new GetClientByIdQuery(new ClientId(clientId));
        var client = clientQueryService.handle(query);
        if (client.isEmpty()) {
            var error = ApplicationError.notFound("Client", clientId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(ClientResourceFromEntityAssembler.toResourceFromEntity(client.get()));
    }
}
