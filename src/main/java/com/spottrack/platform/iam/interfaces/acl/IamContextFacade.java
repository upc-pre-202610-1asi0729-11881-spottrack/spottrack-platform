package com.spottrack.platform.iam.interfaces.acl;

import java.util.Optional;

public interface IamContextFacade {
    Optional<Long> fetchUserIdByUsername(String username);
    boolean existsUserByUsername(String username);
}
