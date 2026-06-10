package com.spottrack.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CreateClientRequest",
        description = "Request payload for creating a new client profile",
        example = "{\"userId\": 1, \"firstName\": \"Jane\", \"lastName\": \"Doe\", \"email\": \"jane.doe@example.com\", \"phoneNumber\": \"987654321\", \"dni\": \"12345678\"}"
)
public record CreateClientResource(

        @NotNull
        @Schema(description = "IAM user identifier", example = "1")
        Long userId,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Client first name", example = "Jane", minLength = 1, maxLength = 50)
        String firstName,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Client last name", example = "Doe", minLength = 1, maxLength = 50)
        String lastName,

        @NotBlank(message = "{validation.not-blank}")
        @Email(message = "{validation.email}")
        @Schema(description = "Client email address", example = "jane.doe@example.com")
        String email,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Client phone number (7-15 digits)", example = "987654321")
        String phoneNumber,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Client DNI (8 digits)", example = "12345678")
        String dni
) {
}
