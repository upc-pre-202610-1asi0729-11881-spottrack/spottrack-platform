package com.spottrack.platform.monitoring.domain.model.commands;

import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;

import java.time.LocalTime;

/**
 *
 * @param sessionTrackerId
 * @param activity Represents the actual time that the user has been actively using an equipment
 * @param inactivity Represents the time where the user was idle
 */
public record CalculateSessionTimeCommand(SessionTrackerId sessionTrackerId, LocalTime activity, LocalTime inactivity) {
}
