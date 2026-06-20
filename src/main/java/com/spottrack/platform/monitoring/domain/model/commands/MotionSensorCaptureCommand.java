package com.spottrack.platform.monitoring.domain.model.commands;

public record MotionSensorCaptureCommand(
        boolean movementDetectedViaSensor
) {
}
