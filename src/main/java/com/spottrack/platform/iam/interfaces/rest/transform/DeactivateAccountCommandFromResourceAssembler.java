package com.spottrack.platform.iam.interfaces.rest.transform;

import com.spottrack.platform.iam.domain.model.commands.DeactivateAccountCommand;
import com.spottrack.platform.iam.interfaces.rest.resources.DeactivateAccountResource;

public class DeactivateAccountCommandFromResourceAssembler {

    public static DeactivateAccountCommand toCommandFromResource(DeactivateAccountResource resource) {
        return new DeactivateAccountCommand(resource.username());
    }
}
