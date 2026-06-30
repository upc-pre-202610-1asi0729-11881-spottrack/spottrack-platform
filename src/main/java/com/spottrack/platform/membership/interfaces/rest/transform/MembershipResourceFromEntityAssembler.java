package com.spottrack.platform.membership.interfaces.rest.transform;

import com.spottrack.platform.membership.domain.model.aggregates.Membership;
import com.spottrack.platform.membership.interfaces.rest.resources.MembershipResource;

public class MembershipResourceFromEntityAssembler {

    public static MembershipResource toResourceFromEntity(Membership membership) {
        return new MembershipResource(
                membership.getId(),
                membership.getMembershipId().uuid(),
                membership.getClientId(),
                membership.getMembershipTier().name(),
                membership.getPrice().amount(),
                membership.getPrice().currency(),
                membership.getMembershipPeriod().startDate(),
                membership.getMembershipPeriod().endDate(),
                membership.getStatus().name()
        );
    }
}
