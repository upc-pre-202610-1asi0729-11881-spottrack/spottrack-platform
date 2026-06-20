package com.spottrack.platform.profiles.interfaces.rest;

import com.spottrack.platform.profiles.application.commandservices.AdminCommandService;
import com.spottrack.platform.profiles.application.queryservices.AdminQueryService;
import com.spottrack.platform.profiles.domain.model.queries.GetAdminByIdQuery;
import com.spottrack.platform.profiles.domain.model.valueobjects.AdminId;
import com.spottrack.platform.profiles.interfaces.rest.resources.AdminResource;
import com.spottrack.platform.profiles.interfaces.rest.resources.CreateAdminResource;
import com.spottrack.platform.profiles.interfaces.rest.resources.UpdateAdminProfileResource;
import com.spottrack.platform.profiles.interfaces.rest.transform.AdminResourceFromEntityAssembler;
import com.spottrack.platform.profiles.interfaces.rest.transform.CreateAdminCommandFromResourceAssembler;
import com.spottrack.platform.profiles.interfaces.rest.transform.UpdateAdminProfileCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/profiles/admins", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admins", description = "Admin profile management endpoints")
public class AdminsController {

    private final AdminCommandService adminCommandService;
    private final AdminQueryService adminQueryService;

    public AdminsController(AdminCommandService adminCommandService, AdminQueryService adminQueryService) {
        this.adminCommandService = adminCommandService;
        this.adminQueryService = adminQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new admin",
            description = "Creates a new admin profile with personal and contact information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Admin created successfully",
                    content = @Content(schema = @Schema(implementation = AdminResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict - admin already exists")
    })
    public ResponseEntity<?> createAdmin(@Valid @RequestBody CreateAdminResource resource) {
        var command = CreateAdminCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = adminCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AdminResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{adminId}")
    @Operation(
            summary = "Update admin profile",
            description = "Updates personal data (name, phone number, DNI) for an existing admin profile."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Admin profile updated successfully",
                    content = @Content(schema = @Schema(implementation = AdminResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Admin not found")
    })
    public ResponseEntity<?> updateAdminProfile(
            @PathVariable
            @Parameter(description = "Admin unique identifier", example = "1", required = true)
            Long adminId,
            @Valid @RequestBody UpdateAdminProfileResource resource
    ) {
        var command = UpdateAdminProfileCommandFromResourceAssembler.toCommandFromResource(adminId, resource);
        var result = adminCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AdminResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/{adminId}")
    @Operation(
            summary = "Get admin by ID",
            description = "Retrieves an admin profile by unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Admin found",
                    content = @Content(schema = @Schema(implementation = AdminResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Admin not found")
    })
    public ResponseEntity<?> getAdminById(
            @PathVariable
            @Parameter(description = "Admin unique identifier", example = "1", required = true)
            Long adminId
    ) {
        var query = new GetAdminByIdQuery(new AdminId(adminId));
        var admin = adminQueryService.handle(query);
        if (admin.isEmpty()) {
            var error = ApplicationError.notFound("Admin", adminId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(AdminResourceFromEntityAssembler.toResourceFromEntity(admin.get()));
    }
}
