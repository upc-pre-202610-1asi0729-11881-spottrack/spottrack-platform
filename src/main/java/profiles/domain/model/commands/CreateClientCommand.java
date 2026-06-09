package profiles.domain.model.commands;

import profiles.domain.model.valueobjects.DNI;
import profiles.domain.model.valueobjects.EmailAddress;
import profiles.domain.model.valueobjects.PhoneNumber;

public record CreateClientCommand(
        Long userId,
        EmailAddress emailAddress,
        String firstName,
        String lastName,
        PhoneNumber phoneNumber,
        DNI dni
) { }
