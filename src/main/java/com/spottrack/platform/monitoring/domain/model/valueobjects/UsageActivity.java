package com.spottrack.platform.monitoring.domain.model.valueobjects;

import java.time.LocalTime;

public record UsageActivity(
        LocalTime continuousActivity, LocalTime seconds
) {
}
