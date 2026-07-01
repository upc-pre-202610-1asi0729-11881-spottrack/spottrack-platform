package com.spottrack.platform.monitoring.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record MotionSensorResource(
        @Schema Long id,
        @Schema String motionSensorId,
        @Schema String equipmentId,
        @Schema LocalDateTime registeredAt
) {
}
