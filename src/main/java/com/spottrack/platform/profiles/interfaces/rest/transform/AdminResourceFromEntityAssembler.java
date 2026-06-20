package com.spottrack.platform.profiles.interfaces.rest.transform;

import com.spottrack.platform.profiles.domain.model.aggregates.Admin;
import com.spottrack.platform.profiles.interfaces.rest.resources.AdminResource;

public class AdminResourceFromEntityAssembler {

    public static AdminResource toResourceFromEntity(Admin entity) {
        return new AdminResource(
                entity.getId(),
                entity.getFullName(),
                entity.getEmailAddress());
    }
}
