package com.spottrack.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Resource for deactivating a user account")
public record DeactivateAccountResource(
        @Schema(description = "Username (email) of the account to deactivate")
        @NotBlank String username
) {
}
