package com.spottrack.platform.membership.domain.model.events;

import com.spottrack.platform.membership.domain.model.aggregates.Membership;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipType;

import java.util.UUID;

public record GymMembershipRenewedEvent(
        Long id,
        UUID membershipId,
        Long clientId,
        MembershipType membershipType
) {
    public static GymMembershipRenewedEvent from(Membership membership) {
        return new GymMembershipRenewedEvent(
                membership.getId(),
                membership.getMembershipId().uuid(),
                membership.getClientId(),
                membership.getMembershipType()
        );
    }
}
