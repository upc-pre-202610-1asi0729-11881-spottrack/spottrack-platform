package com.spottrack.platform.monitoring.domain.model.commands;

import com.spottrack.platform.monitoring.domain.model.valueObjects.ReservationId;
import com.spottrack.platform.monitoring.domain.model.valueObjects.SessionTrackerId;
import com.spottrack.platform.monitoring.domain.model.valueObjects.UsageActivity;

public record CreateSessionTrackerCommand(SessionTrackerId sessionTrackerId, ReservationId reservationId, boolean sessionIsActive, boolean sessionIsInactive, UsageActivity usageActivity) {
}
