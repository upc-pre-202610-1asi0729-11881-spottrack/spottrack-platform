package com.spottrack.platform.monitoring.domain.model.commands;

import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyReportId;

public record ResolveAnomalyReportCommand(AnomalyReportId anomalyReportId) {
}
