package com.spottrack.platform.profiles.domain.model.commands;

import com.spottrack.platform.profiles.domain.model.valueobjects.EmailAddress;

public record CreateAdminCommand(
        Long userId,
        EmailAddress emailAddress
) { }
