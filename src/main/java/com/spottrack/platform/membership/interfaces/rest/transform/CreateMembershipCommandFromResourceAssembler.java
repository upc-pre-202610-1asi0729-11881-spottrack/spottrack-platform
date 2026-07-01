package com.spottrack.platform.membership.interfaces.rest.transform;

import com.spottrack.platform.membership.domain.model.commands.CreateMembershipCommand;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipTier;
import com.spottrack.platform.membership.interfaces.rest.resources.CreateMembershipResource;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;

public class CreateMembershipCommandFromResourceAssembler {

    public static CreateMembershipCommand toCommandFromResource(CreateMembershipResource resource) {
        return new CreateMembershipCommand(
                resource.clientId(),
                MembershipTier.valueOf(resource.membershipTier()),
                new Money(resource.priceAmount(), resource.priceCurrency()),
                resource.startDate(),
                resource.endDate()
        );
    }
}
