package com.spottrack.platform.membership.application.internal.queryservices;

import com.spottrack.platform.membership.application.queryservices.MembershipQueryService;
import com.spottrack.platform.membership.domain.model.aggregates.Membership;
import com.spottrack.platform.membership.domain.model.queries.GetMembershipByIdQuery;
import com.spottrack.platform.membership.domain.model.queries.GetMembershipsByClientIdQuery;
import com.spottrack.platform.membership.domain.repositories.MembershipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipQueryServiceImpl implements MembershipQueryService {

    private final MembershipRepository membershipRepository;

    public MembershipQueryServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Optional<Membership> handle(GetMembershipByIdQuery query) {
        return membershipRepository.findByMembershipId(query.membershipId());
    }

    @Override
    public List<Membership> handle(GetMembershipsByClientIdQuery query) {
        return membershipRepository.findByClientId(query.clientId());
    }
}
