package com.spottrack.platform.membership.application.queryservices;

import com.spottrack.platform.membership.domain.model.aggregates.Membership;
import com.spottrack.platform.membership.domain.model.queries.GetMembershipByIdQuery;
import com.spottrack.platform.membership.domain.model.queries.GetMembershipsByClientIdQuery;

import java.util.List;
import java.util.Optional;

public interface MembershipQueryService {
    Optional<Membership> handle(GetMembershipByIdQuery query);
    List<Membership> handle(GetMembershipsByClientIdQuery query);
}
