package com.spottrack.platform.routine.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "StartRoutineRequest",
        description = "Request payload for starting a routine session",
        example = "{\"routineId\": 1, \"clientId\": 1}"
)
public record StartRoutineResource(

        @NotNull
        @Schema(description = "Routine identifier", example = "1")
        Long routineId,

        @NotNull
        @Schema(description = "Client identifier", example = "1")
        Long clientId
) {}
