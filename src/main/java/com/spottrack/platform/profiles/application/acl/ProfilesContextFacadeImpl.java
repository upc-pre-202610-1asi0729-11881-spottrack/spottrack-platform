package com.spottrack.platform.profiles.application.acl;

import com.spottrack.platform.profiles.application.commandservices.AdminCommandService;
import com.spottrack.platform.profiles.application.commandservices.BusinessProfileCommandService;
import com.spottrack.platform.profiles.application.queryservices.AdminQueryService;
import com.spottrack.platform.profiles.application.queryservices.ClientQueryService;
import com.spottrack.platform.profiles.domain.model.commands.CreateAdminCommand;
import com.spottrack.platform.profiles.domain.model.commands.CreateBusinessProfileCommand;
import com.spottrack.platform.profiles.domain.model.queries.GetAdminByEmailQuery;
import com.spottrack.platform.profiles.domain.model.queries.GetAdminByUserIdQuery;
import com.spottrack.platform.profiles.domain.model.queries.GetClientByEmailQuery;
import com.spottrack.platform.profiles.domain.model.valueobjects.BusinessInfo;
import com.spottrack.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.spottrack.platform.profiles.domain.model.valueobjects.PhoneNumber;
import com.spottrack.platform.profiles.interfaces.acl.ProfilesContextFacade;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfilesContextFacadeImpl implements ProfilesContextFacade {

    private final ClientQueryService clientQueryService;
    private final AdminQueryService adminQueryService;
    private final AdminCommandService adminCommandService;
    private final BusinessProfileCommandService businessProfileCommandService;

    public ProfilesContextFacadeImpl(ClientQueryService clientQueryService,
                                     AdminQueryService adminQueryService,
                                     AdminCommandService adminCommandService,
                                     BusinessProfileCommandService businessProfileCommandService) {
        this.clientQueryService = clientQueryService;
        this.adminQueryService = adminQueryService;
        this.adminCommandService = adminCommandService;
        this.businessProfileCommandService = businessProfileCommandService;
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

    @Override
    public void provisionAdminProfile(Long userId, String email) {
        var existing = adminQueryService.handle(new GetAdminByUserIdQuery(userId));
        if (existing.isPresent()) {
            log.info("Admin profile already exists for userId {}, skipping", userId);
            return;
        }
        var result = adminCommandService.handle(new CreateAdminCommand(userId, new EmailAddress(email)));
        switch (result) {
            case Result.Success<?, ?> ignored ->
                    log.info("Admin profile provisioned for userId {}", userId);
            case Result.Failure<?, ApplicationError> f -> {
                log.error("Failed to provision Admin profile for userId {}: {}", userId, f.error());
                throw new RuntimeException("Admin provisioning failed: " + f.error().message());
            }
        }
    }

    @Override
    public void provisionBusinessProfile(Long userId, String companyName, String ruc,
                                         String legalStructure, String companyPhone, String companyEmail,
                                         String streetAddress, String city, String district) {
        var businessInfo = new BusinessInfo(
                companyName, ruc, legalStructure,
                new PhoneNumber(companyPhone),
                new EmailAddress(companyEmail),
                streetAddress, city, district
        );
        var result = businessProfileCommandService.handle(new CreateBusinessProfileCommand(userId, businessInfo));
        switch (result) {
            case Result.Success<?, ?> ignored ->
                    log.info("BusinessProfile provisioned for userId {}", userId);
            case Result.Failure<?, ApplicationError> f -> {
                if (f.error().code().endsWith("_CONFLICT")) {
                    log.info("BusinessProfile already exists for userId {}, skipping", userId);
                    return;
                }
                log.error("Failed to provision BusinessProfile for userId {}: {}", userId, f.error());
                throw new RuntimeException("BusinessProfile provisioning failed: " + f.error().message());
            }
        }
    }
}
