package com.spottrack.platform.iam.application.acl;

import com.spottrack.platform.iam.application.queryservices.UserQueryService;
import com.spottrack.platform.iam.domain.model.queries.GetUserByUsernameQuery;
import com.spottrack.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IamContextFacadeImpl implements IamContextFacade {

    private final UserQueryService userQueryService;

    public IamContextFacadeImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Optional<Long> fetchUserIdByUsername(String username) {
        return userQueryService.handle(new GetUserByUsernameQuery(username))
                .map(user -> user.getId());
    }

    @Override
    public boolean existsUserByUsername(String username) {
        return userQueryService.handle(new GetUserByUsernameQuery(username)).isPresent();
    }
}
