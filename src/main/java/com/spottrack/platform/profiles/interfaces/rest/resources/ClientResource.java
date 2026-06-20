package com.spottrack.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ClientResponse",
        description = "Client profile information response",
        example = "{\"id\": 1, \"fullName\": \"Jane Doe\", \"email\": \"jane.doe@example.com\"}"
)
public record ClientResource(

        @Schema(description = "Client unique identifier", example = "1")
        Long id,

        @Schema(description = "Client full name", example = "Jane Doe")
        String fullName,

        @Schema(description = "Client email address", example = "jane.doe@example.com")
        String email
) {
}
