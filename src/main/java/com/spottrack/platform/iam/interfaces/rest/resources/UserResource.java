package com.spottrack.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "User resource representation")
public record UserResource(
        @Schema(description = "User identifier") Long id,
        @Schema(description = "Username (email)") String username,
        @Schema(description = "Assigned role names") List<String> roles
) {
}
