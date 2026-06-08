package com.spottrack.platform.routine.domain.model.commands;
import com.spottrack.platform.routine.domain.model.valueobjects.ProfileId;
import com.spottrack.platform.routine.domain.model.valueobjects.RoutineName;

public record CreateRoutineCommand(
        RoutineName routineName,
        ProfileId profileId
) {}
