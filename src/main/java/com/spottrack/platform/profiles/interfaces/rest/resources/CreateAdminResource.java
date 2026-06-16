package com.spottrack.platform.profiles.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CreateAdminRequest",
        description = "Request payload for creating a new admin profile",
        example = "{\"userId\": 2, \"email\": \"john.smith@example.com\"}"
)
public record CreateAdminResource(

        @NotNull
        @Schema(description = "IAM user identifier", example = "2")
        Long userId,

        @NotBlank(message = "{validation.not-blank}")
        @Email(message = "{validation.email}")
        @Schema(description = "Admin email address", example = "john.smith@example.com")
        String email
) {
}
