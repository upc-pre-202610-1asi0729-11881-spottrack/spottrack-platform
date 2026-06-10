package com.spottrack.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "AdminResponse",
        description = "Admin profile information response",
        example = "{\"id\": 1, \"fullName\": \"John Smith\", \"email\": \"john.smith@example.com\"}"
)
public record AdminResource(

        @Schema(description = "Admin unique identifier", example = "1")
        Long id,

        @Schema(description = "Admin full name", example = "John Smith")
        String fullName,

        @Schema(description = "Admin email address", example = "john.smith@example.com")
        String email
) {
}
