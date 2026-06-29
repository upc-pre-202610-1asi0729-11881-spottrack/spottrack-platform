package com.spottrack.platform.membership.domain.model.aggregates;

import com.spottrack.platform.membership.domain.model.valueobjects.MembershipId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

public class Membership extends AbstractDomainAggregateRoot<Membership> {

    Long id; // persistenceId
    MembershipId membershipId; // DomainId



    public Membership() {

    }

}
