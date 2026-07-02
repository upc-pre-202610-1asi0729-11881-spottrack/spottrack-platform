package com.spottrack.platform.monitoring.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterCameraSensorResource(
        @Schema(description = "Identifier of the zone this camera sensor watches")
        String zoneId
) {
}
