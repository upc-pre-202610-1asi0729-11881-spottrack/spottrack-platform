package com.spottrack.platform.membership.domain.model.commands;

import com.spottrack.platform.membership.domain.model.valueobjects.MembershipId;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipType;

public record UpgradeMembershipPlanCommand(MembershipId membershipId, MembershipType newMembershipType) {
    public UpgradeMembershipPlanCommand {
        if (membershipId == null) throw new IllegalArgumentException("membership.error.membershipId.notNull");
        if (newMembershipType == null) throw new IllegalArgumentException("membership.error.membershipType.notNull");
    }
}
