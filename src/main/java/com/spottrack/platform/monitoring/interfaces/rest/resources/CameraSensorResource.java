package com.spottrack.platform.monitoring.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CameraSensorResource(
        @Schema Long id,
        @Schema String cameraSensorId,
        @Schema String zoneId,
        @Schema LocalDateTime registeredAt
) {
}
