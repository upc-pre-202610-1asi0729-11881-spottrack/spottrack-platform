package com.spottrack.platform.monitoring.domain.model.commands;

import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyType;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;

public record ReportAnomalyManuallyCommand(
        SessionTrackerId sessionTrackerId,
        AnomalyType anomalyType,
        String description
) {
}
