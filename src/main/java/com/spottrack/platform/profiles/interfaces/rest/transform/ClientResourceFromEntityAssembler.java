package com.spottrack.platform.profiles.interfaces.rest.transform;

import com.spottrack.platform.profiles.domain.model.aggregates.Client;
import com.spottrack.platform.profiles.interfaces.rest.resources.ClientResource;

public class ClientResourceFromEntityAssembler {

    public static ClientResource toResourceFromEntity(Client entity) {
        return new ClientResource(
                entity.getId(),
                entity.getFullName(),
                entity.getEmailAddress());
    }
}
