package com.spottrack.platform.iam.interfaces.acl;

import com.spottrack.platform.iam.domain.model.commands.SavePendingRegistrationCommand;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

import java.util.Optional;
import java.util.UUID;

public interface IamContextFacade {
    Optional<Long> fetchUserIdByUsername(String username);
    boolean existsUserByUsername(String username);

    Result<UUID, ApplicationError> savePendingRegistration(SavePendingRegistrationCommand command);
    boolean existsPendingRegistrationByEmail(String email);

    Long consumePendingRegistration(UUID registrationId);
    String fetchPendingRegistrationEmail(UUID registrationId);
    String fetchPendingRegistrationCompanyName(UUID registrationId);
    String fetchPendingRegistrationRuc(UUID registrationId);
    String fetchPendingRegistrationLegalStructure(UUID registrationId);
    String fetchPendingRegistrationCompanyPhone(UUID registrationId);
    String fetchPendingRegistrationCompanyEmail(UUID registrationId);
    String fetchPendingRegistrationStreetAddress(UUID registrationId);
    String fetchPendingRegistrationCity(UUID registrationId);
    String fetchPendingRegistrationDistrict(UUID registrationId);
}
