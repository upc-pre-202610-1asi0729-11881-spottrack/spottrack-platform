package com.spottrack.platform.profiles.domain.model.commands;

import com.spottrack.platform.profiles.domain.model.valueobjects.DNI;
import com.spottrack.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.spottrack.platform.profiles.domain.model.valueobjects.PhoneNumber;

public record CreateAdminCommand(
        Long userId,
        EmailAddress emailAddress,
        String firstName,
        String lastName,
        PhoneNumber phoneNumber,
        DNI dni
) {}