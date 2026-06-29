package com.spottrack.platform.membership.domain.model.events;

import com.spottrack.platform.membership.domain.model.aggregates.Membership;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipTier;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipType;

import java.util.UUID;

public record GymMembershipActivatedEvent(
        Long id,
        UUID membershipId,
        Long clientId,
        MembershipTier membershipType
) {
    public static GymMembershipActivatedEvent from(Membership membership) {
        return new GymMembershipActivatedEvent(
                membership.getId(),
                membership.getMembershipId().uuid(),
                membership.getClientId(),
                membership.getMembershipId()
        );
    }
}
