package com.spottrack.platform.profiles.interfaces.acl;

public interface ProfilesContextFacade {
    Long fetchClientIdByEmail(String email);
    Long fetchAdminIdByEmail(String email);

    void provisionAdminProfile(Long userId, String email);

    void provisionBusinessProfile(Long userId, String companyName, String ruc,
                                  String legalStructure, String companyPhone, String companyEmail,
                                  String streetAddress, String city, String district);
}
