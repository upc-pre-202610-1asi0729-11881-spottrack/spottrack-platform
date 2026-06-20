package com.spottrack.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Resource for signing out a user")
public record SignOutResource(
        @Schema(description = "Username (email) of the account to sign out")
        @NotBlank String username
) {
}
