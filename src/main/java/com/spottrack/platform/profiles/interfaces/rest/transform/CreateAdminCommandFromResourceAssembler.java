package com.spottrack.platform.profiles.interfaces.rest.transform;

import com.spottrack.platform.profiles.domain.model.commands.CreateAdminCommand;
import com.spottrack.platform.profiles.domain.model.valueobjects.DNI;
import com.spottrack.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.spottrack.platform.profiles.domain.model.valueobjects.PhoneNumber;
import com.spottrack.platform.profiles.interfaces.rest.resources.CreateAdminResource;

public class CreateAdminCommandFromResourceAssembler {

    public static CreateAdminCommand toCommandFromResource(CreateAdminResource resource) {
        return new CreateAdminCommand(
                resource.userId(),
                new EmailAddress(resource.email()),
                resource.firstName(),
                resource.lastName(),
                new PhoneNumber(resource.phoneNumber()),
                new DNI(resource.dni()));
    }
}
