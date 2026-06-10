package com.spottrack.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CreateAdminRequest",
        description = "Request payload for creating a new admin profile",
        example = "{\"userId\": 2, \"firstName\": \"John\", \"lastName\": \"Smith\", \"email\": \"john.smith@example.com\", \"phoneNumber\": \"123456789\", \"dni\": \"87654321\"}"
)
public record CreateAdminResource(

        @NotNull
        @Schema(description = "IAM user identifier", example = "2")
        Long userId,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Admin first name", example = "John", minLength = 1, maxLength = 50)
        String firstName,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Admin last name", example = "Smith", minLength = 1, maxLength = 50)
        String lastName,

        @NotBlank(message = "{validation.not-blank}")
        @Email(message = "{validation.email}")
        @Schema(description = "Admin email address", example = "john.smith@example.com")
        String email,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Admin phone number (7-15 digits)", example = "123456789")
        String phoneNumber,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Admin DNI (8 digits)", example = "87654321")
        String dni
) {
}
