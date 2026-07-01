package com.spottrack.platform.iam.interfaces.acl.dto;

public record ProvisionedAccountDto(
        Long userId,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        String dni,
        String companyName,
        String ruc,
        String legalStructure,
        String companyPhone,
        String companyEmail,
        String streetAddress,
        String city,
        String district,
        String membershipTier
) {
}
