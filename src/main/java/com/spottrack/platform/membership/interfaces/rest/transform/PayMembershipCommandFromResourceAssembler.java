package com.spottrack.platform.membership.interfaces.rest.transform;

import com.spottrack.platform.membership.domain.model.commands.PayMembershipCommand;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipTier;
import com.spottrack.platform.membership.interfaces.rest.resources.PayMembershipResource;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;

public class PayMembershipCommandFromResourceAssembler {

    public static PayMembershipCommand toCommand(PayMembershipResource resource) {
        return new PayMembershipCommand(
                resource.userId(),
                MembershipTier.valueOf(resource.membershipTier()),
                new Money(resource.amount(), resource.currency())
        );
    }
}
