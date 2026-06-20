package com.spottrack.platform.monitoring.domain.model.commands;

import com.spottrack.platform.monitoring.domain.model.valueobjects.UsageActivity;

import java.time.LocalTime;

public record VerifyUsageSessionCommand(UsageActivity usageActivity, LocalTime usageThreshold, LocalTime inactivityTimer) {

}
