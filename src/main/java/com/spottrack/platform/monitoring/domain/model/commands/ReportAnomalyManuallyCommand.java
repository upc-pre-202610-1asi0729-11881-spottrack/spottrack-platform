package com.spottrack.platform.monitoring.domain.model.commands;

import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;

public record ReportAnomalyManuallyCommand(
        SessionTrackerId sessionTrackerId,
        String anomalyType,
        String description
) {
}
