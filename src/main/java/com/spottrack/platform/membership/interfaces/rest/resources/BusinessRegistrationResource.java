package com.spottrack.platform.membership.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BusinessRegistrationResource(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String phoneNumber,
        @NotBlank String dni,
        @NotBlank String companyName,
        @NotBlank String ruc,
        @NotBlank String legalStructure,
        @NotBlank String companyPhone,
        @NotBlank String companyEmail,
        @NotBlank String streetAddress,
        @NotBlank String city,
        @NotBlank String district,
        @NotBlank String membershipTier,
        @NotNull @Positive BigDecimal amount,
        @NotBlank String currency
) {}
