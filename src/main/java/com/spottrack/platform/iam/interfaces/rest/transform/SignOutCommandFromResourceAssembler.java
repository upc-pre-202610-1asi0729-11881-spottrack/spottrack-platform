package com.spottrack.platform.iam.interfaces.rest.transform;

import com.spottrack.platform.iam.domain.model.commands.SignOutCommand;
import com.spottrack.platform.iam.interfaces.rest.resources.SignOutResource;

public class SignOutCommandFromResourceAssembler {

    public static SignOutCommand toCommandFromResource(SignOutResource resource) {
        return new SignOutCommand(resource.username());
    }
}
