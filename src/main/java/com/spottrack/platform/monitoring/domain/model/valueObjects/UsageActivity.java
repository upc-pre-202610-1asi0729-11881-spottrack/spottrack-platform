package com.spottrack.platform.monitoring.domain.model.valueObjects;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public record UsageActivity(
        LocalTime continuousActivity, LocalTime seconds
) {
}
