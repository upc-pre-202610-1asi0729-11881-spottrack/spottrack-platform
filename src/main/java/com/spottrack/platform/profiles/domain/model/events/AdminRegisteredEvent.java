package com.spottrack.platform.profiles.domain.model.events;

import com.spottrack.platform.profiles.domain.model.aggregates.Admin;

public record AdminRegisteredEvent(
        Long adminId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String dni
) {
    public static AdminRegisteredEvent from(Admin admin) {
        var info = admin.getPersonInfo();
        return new AdminRegisteredEvent(
                admin.getId(),
                info.firstName(),
                info.lastName(),
                admin.getEmailAddress(),
                info.phoneNumber().phoneNumber(),
                info.dni().dni()
        );
    }
}