package profiles.domain.model.commands;

import profiles.domain.model.valueobjects.ClientId;
import profiles.domain.model.valueobjects.EmailAddress;
import profiles.domain.model.valueobjects.PhoneNumber;

public record UpdateClientProfileCommand(
        ClientId clientId,
        String firstName,
        String lastName,
        PhoneNumber phoneNumber
){ }
