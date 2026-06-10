package com.spottrack.platform.profiles.interfaces.acl;

public interface ProfilesContextFacade {
    Long fetchClientIdByEmail(String email);
    Long fetchAdminIdByEmail(String email);
}
