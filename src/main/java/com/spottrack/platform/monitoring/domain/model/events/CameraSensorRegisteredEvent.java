package com.spottrack.platform.monitoring.domain.model.events;

import com.spottrack.platform.monitoring.domain.model.valueobjects.CameraSensorId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ZoneId;

public record CameraSensorRegisteredEvent(CameraSensorId cameraSensorId, ZoneId zoneId) {
}
