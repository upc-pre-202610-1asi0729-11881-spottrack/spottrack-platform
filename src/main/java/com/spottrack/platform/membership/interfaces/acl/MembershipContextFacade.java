package com.spottrack.platform.membership.interfaces.acl;

public interface MembershipContextFacade {

    /**
     * Returns "ACTIVE", "SUSPENDED", or "INACTIVE" (includes no membership, CANCELLED, EXPIRED).
     */
    String fetchMembershipAccessStatus(Long clientId);
}
