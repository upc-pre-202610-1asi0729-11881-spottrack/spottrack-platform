package com.spottrack.platform.iam.interfaces.acl;

import com.spottrack.platform.iam.domain.model.commands.SavePendingRegistrationCommand;
import com.spottrack.platform.iam.interfaces.acl.dto.PendingRegistrationDto;
import com.spottrack.platform.iam.interfaces.acl.dto.ProvisionedAccountDto;

import java.util.Optional;
import java.util.UUID;

public interface IamContextFacade {
    Optional<Long> fetchUserIdByUsername(String username);
    boolean existsUserByUsername(String username);

    UUID savePendingRegistration(SavePendingRegistrationCommand command);
    Optional<PendingRegistrationDto> findPendingRegistrationById(UUID registrationId);
    boolean existsPendingRegistrationByEmail(String email);

    /**
     * Atomically consumes a pending registration: creates the IAM User account and
     * marks the registration as CONSUMED. Idempotent — if already CONSUMED, returns
     * the existing User data without creating duplicates.
     */
    Optional<ProvisionedAccountDto> consumePendingRegistration(UUID registrationId);
}
