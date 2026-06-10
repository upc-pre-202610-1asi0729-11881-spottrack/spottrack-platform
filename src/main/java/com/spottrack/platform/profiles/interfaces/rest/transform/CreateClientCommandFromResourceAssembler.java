package com.spottrack.platform.profiles.interfaces.rest.transform;

import com.spottrack.platform.profiles.domain.model.commands.CreateClientCommand;
import com.spottrack.platform.profiles.domain.model.valueobjects.DNI;
import com.spottrack.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.spottrack.platform.profiles.domain.model.valueobjects.PhoneNumber;
import com.spottrack.platform.profiles.interfaces.rest.resources.CreateClientResource;

public class CreateClientCommandFromResourceAssembler {

    public static CreateClientCommand toCommandFromResource(CreateClientResource resource) {
        return new CreateClientCommand(
                resource.userId(),
                new EmailAddress(resource.email()),
                resource.firstName(),
                resource.lastName(),
                new PhoneNumber(resource.phoneNumber()),
                new DNI(resource.dni()));
    }
}
