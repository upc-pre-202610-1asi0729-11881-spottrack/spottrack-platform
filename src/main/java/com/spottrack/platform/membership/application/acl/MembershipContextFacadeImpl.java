package com.spottrack.platform.membership.application.acl;

import com.spottrack.platform.membership.domain.model.valueobjects.MembershipStatus;
import com.spottrack.platform.membership.domain.repositories.MembershipRepository;
import com.spottrack.platform.membership.interfaces.acl.MembershipContextFacade;
import org.springframework.stereotype.Service;

@Service
public class MembershipContextFacadeImpl implements MembershipContextFacade {

    private final MembershipRepository membershipRepository;

    public MembershipContextFacadeImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public String fetchMembershipAccessStatus(Long clientId) {
        var memberships = membershipRepository.findByClientId(clientId);
        if (memberships.stream().anyMatch(m -> m.getStatus() == MembershipStatus.ACTIVE)) {
            return "ACTIVE";
        }
        if (memberships.stream().anyMatch(m -> m.getStatus() == MembershipStatus.SUSPENDED)) {
            return "SUSPENDED";
        }
        return "INACTIVE";
    }
}
