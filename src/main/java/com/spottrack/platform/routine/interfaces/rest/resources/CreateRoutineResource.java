package com.spottrack.platform.routine.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CreateRoutineRequest",
        description = "Request payload for creating a new routine",
        example = "{\"routineName\": \"Morning Workout\", \"clientId\": 1}"
)
public record CreateRoutineResource(

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Name of the routine", example = "Morning Workout", minLength = 1, maxLength = 100)
        String routineName,

        @NotNull
        @Schema(description = "Client identifier", example = "1")
        Long clientId
) {
}
