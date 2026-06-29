package com.spottrack.platform.membership.domain.model.aggregates;

import com.spottrack.platform.membership.domain.model.valueobjects.MembershipId;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipType;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;

public class Membership extends AbstractDomainAggregateRoot<Membership> {

    Long id; // persistenceId
    MembershipId membershipId; // DomainId
    MembershipType membershipType;
    Money price;
    


    public Membership() {

    }
}
