package com.spottrack.platform.monitoring.domain.model.commands;

public record CameraCaptureMotionCommand(
        boolean movementDetectedViaVideo
) {
}
