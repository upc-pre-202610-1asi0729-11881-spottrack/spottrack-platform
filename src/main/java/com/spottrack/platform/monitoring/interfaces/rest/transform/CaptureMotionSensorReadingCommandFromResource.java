package com.spottrack.platform.monitoring.interfaces.rest.transform;

import com.spottrack.platform.monitoring.domain.model.commands.MotionSensorCaptureCommand;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;
import com.spottrack.platform.monitoring.interfaces.rest.resources.CaptureMotionSensorReadingResource;

public class CaptureMotionSensorReadingCommandFromResource {
    public static MotionSensorCaptureCommand toCommandFromResource(CaptureMotionSensorReadingResource resource) {
        return new MotionSensorCaptureCommand(new SessionTrackerId(resource.sessionTrackerId()), resource.movementDetectedViaSensor());
    }
}
