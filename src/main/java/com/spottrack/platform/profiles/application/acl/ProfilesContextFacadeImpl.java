package com.spottrack.platform.profiles.application.acl;

import com.spottrack.platform.profiles.application.queryservices.AdminQueryService;
import com.spottrack.platform.profiles.application.queryservices.ClientQueryService;
import com.spottrack.platform.profiles.domain.model.queries.GetAdminByEmailQuery;
import com.spottrack.platform.profiles.domain.model.queries.GetClientByEmailQuery;
import com.spottrack.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.spottrack.platform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ProfilesContextFacadeImpl implements ProfilesContextFacade {
    private final ClientQueryService clientQueryService;
    private final AdminQueryService adminQueryService;

    public ProfilesContextFacadeImpl(ClientQueryService clientQueryService, AdminQueryService adminQueryService) {
        this.clientQueryService = clientQueryService;
        this.adminQueryService = adminQueryService;
    }

    @Override
    public Long fetchClientIdByEmail(String email) {
        var query = new GetClientByEmailQuery(new EmailAddress(email));
        var client = clientQueryService.handle(query);
        return client.isEmpty() ? 0L : client.get().getId();
    }

    @Override
    public Long fetchAdminIdByEmail(String email) {
        var query = new GetAdminByEmailQuery(new EmailAddress(email));
        var admin = adminQueryService.handle(query);
        return admin.isEmpty() ? 0L : admin.get().getId();
    }
}
