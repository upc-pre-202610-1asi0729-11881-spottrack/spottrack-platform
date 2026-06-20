package com.spottrack.platform.monitoring.domain.model.commands;

import com.spottrack.platform.monitoring.domain.model.valueobjects.ReservationId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.UsageActivity;

public record CreateSessionTrackerCommand(SessionTrackerId sessionTrackerId, ReservationId reservationId, boolean sessionIsActive, boolean sessionIsInactive, UsageActivity usageActivity) {
}
