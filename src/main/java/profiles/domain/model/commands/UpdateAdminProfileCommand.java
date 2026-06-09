package profiles.domain.model.commands;

import profiles.domain.model.valueobjects.AdminId;
import profiles.domain.model.valueobjects.PhoneNumber;

public record UpdateAdminProfileCommand(
        AdminId adminId,
        String firstName,
        String lastName,
        PhoneNumber phoneNumber
){ }
