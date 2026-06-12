package com.spottrack.platform.profiles.domain.model.commands;

import com.spottrack.platform.profiles.domain.model.valueobjects.Dni;
import com.spottrack.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.spottrack.platform.profiles.domain.model.valueobjects.PhoneNumber;

public record CreateClientCommand(
        Long userId,
        EmailAddress emailAddress,
        String firstName,
        String lastName,
        PhoneNumber phoneNumber,
        Dni dni
) { }
