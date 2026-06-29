package com.spottrack.platform.membership.domain.model.commands;

import com.spottrack.platform.membership.domain.model.valueobjects.MembershipType;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;

import java.time.LocalDate;

public record CreateMembershipCommand(
        Long clientId,
        MembershipType membershipType,
        Money price,
        LocalDate startDate,
        LocalDate endDate
) {
    public CreateMembershipCommand {
        if (clientId == null) throw new IllegalArgumentException("membership.error.clientId.notNull");
        if (membershipType == null) throw new IllegalArgumentException("membership.error.membershipType.notNull");
        if (price == null) throw new IllegalArgumentException("membership.error.price.notNull");
        if (startDate == null) throw new IllegalArgumentException("membership.error.startDate.notNull");
        if (endDate == null) throw new IllegalArgumentException("membership.error.endDate.notNull");
    }
}
