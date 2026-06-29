package com.spottrack.platform.membership.domain.repositories;

import com.spottrack.platform.membership.domain.model.aggregates.Membership;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipId;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository {
    Optional<Membership> findByMembershipId(MembershipId membershipId);
    Optional<Membership> findById(Long id);
    List<Membership> findByClientId(Long clientId);
    Membership save(Membership membership);
    boolean existsByMembershipId(MembershipId membershipId);
}
