package com.spottrack.platform.monitoring.domain.model.events;

public record CameraMotionCapturedEvent(
        boolean movementDetectedViaCamera
) {
}
