package com.spottrack.platform.iam.interfaces.acl.dto;

import java.util.UUID;

public record PendingRegistrationDto(
        UUID registrationId,
        String email,
        String membershipTier,
        boolean expired
) {
}
