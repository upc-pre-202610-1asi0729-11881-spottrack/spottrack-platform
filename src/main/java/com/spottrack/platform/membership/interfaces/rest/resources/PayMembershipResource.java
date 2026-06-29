package com.spottrack.platform.membership.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PayMembershipResource(
        @NotNull Long userId,
        @NotBlank String membershipTier,
        @NotNull @Positive BigDecimal amount,
        @NotBlank String currency
) {}
