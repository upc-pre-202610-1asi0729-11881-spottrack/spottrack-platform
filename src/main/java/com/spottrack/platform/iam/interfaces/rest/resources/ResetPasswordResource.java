package com.spottrack.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Resource for resetting a user's password")
public record ResetPasswordResource(
        @Schema(description = "Username (email) of the account") @NotBlank String username,
        @Schema(description = "New password to set") @NotBlank String newPassword
) {
}
