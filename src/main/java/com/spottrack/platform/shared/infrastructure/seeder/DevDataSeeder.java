package com.spottrack.platform.shared.infrastructure.seeder;

import com.spottrack.platform.analytics.application.commandservices.ActivityReportCommandService;
import com.spottrack.platform.analytics.application.commandservices.MaintenanceQuoteCommandService;
import com.spottrack.platform.analytics.application.commandservices.ROIProjectionCommandService;
import com.spottrack.platform.analytics.application.queryservices.MaintenanceQuoteQueryService;
import com.spottrack.platform.analytics.application.queryservices.ROIProjectionQueryService;
import com.spottrack.platform.analytics.domain.model.commands.RequestADetailedMaintenanceQuoteCommand;
import com.spottrack.platform.analytics.domain.model.commands.RequestActivityAnalysisCommand;
import com.spottrack.platform.analytics.domain.model.commands.RequestDowntimeCostCommand;
import com.spottrack.platform.analytics.domain.model.commands.RequestEarningsCommand;
import com.spottrack.platform.analytics.domain.model.commands.RequestPreventiveCostCommand;
import com.spottrack.platform.analytics.domain.model.commands.RequestRoiCommand;
import com.spottrack.platform.analytics.domain.model.commands.RequestSparePartsCommand;
import com.spottrack.platform.analytics.domain.model.queries.GetAllMaintenanceQuotesQuery;
import com.spottrack.platform.analytics.domain.model.queries.GetAllROIProjectionsQuery;
import com.spottrack.platform.analytics.domain.repositories.ActivityReportRepository;
import com.spottrack.platform.gym.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.gym.application.commandServices.GymCommandService;
import com.spottrack.platform.gym.domain.model.commands.AddBranchCommand;
import com.spottrack.platform.gym.domain.model.commands.AddDniToWhitelistCommand;
import com.spottrack.platform.gym.domain.model.commands.AddZoneCommand;
import com.spottrack.platform.gym.domain.model.commands.CreateGym;
import com.spottrack.platform.gym.domain.model.commands.RegisterEquipment;
import com.spottrack.platform.gym.domain.model.valueobjects.BranchId;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.gym.domain.model.valueobjects.ManufacturerId;
import com.spottrack.platform.gym.domain.model.valueobjects.ZoneId;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.EquipmentPersistenceRepository;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.GymPersistenceRepository;
import com.spottrack.platform.iam.application.commandservices.RoleCommandService;
import com.spottrack.platform.iam.application.commandservices.UserCommandService;
import com.spottrack.platform.iam.domain.model.commands.SeedRolesCommand;
import com.spottrack.platform.iam.domain.model.commands.SignUpCommand;
import com.spottrack.platform.iam.domain.model.entities.Role;
import com.spottrack.platform.iam.domain.model.valueobjects.Roles;
import com.spottrack.platform.iam.domain.repositories.RoleRepository;
import com.spottrack.platform.iam.domain.repositories.UserRepository;
import com.spottrack.platform.maintenance.application.commandServices.MaintenanceCommandService;
import com.spottrack.platform.maintenance.domain.model.commands.CreateTechnicalTicketCommand;
import com.spottrack.platform.maintenance.domain.model.commands.RegisterMaintenanceCompletion;
import com.spottrack.platform.maintenance.domain.model.commands.RequestMaintenance;
import com.spottrack.platform.maintenance.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketPriority;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketType;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.repositories.TechnicalTicketJpaRepository;
import com.spottrack.platform.membership.application.commandservices.MembershipCommandService;
import com.spottrack.platform.membership.domain.model.commands.ActivateMembershipCommand;
import com.spottrack.platform.membership.domain.model.commands.CreateMembershipCommand;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipStatus;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipTier;
import com.spottrack.platform.membership.domain.repositories.MembershipRepository;
import com.spottrack.platform.profiles.application.commandservices.AdminCommandService;
import com.spottrack.platform.profiles.application.commandservices.ClientCommandService;
import com.spottrack.platform.profiles.application.queryservices.AdminQueryService;
import com.spottrack.platform.profiles.application.queryservices.ClientQueryService;
import com.spottrack.platform.profiles.domain.model.commands.AssociateClientWithGymCommand;
import com.spottrack.platform.profiles.domain.model.commands.UpdateAdminProfileCommand;
import com.spottrack.platform.profiles.domain.model.commands.UpdateClientProfileCommand;
import com.spottrack.platform.profiles.domain.model.queries.GetAdminByUserIdQuery;
import com.spottrack.platform.profiles.domain.model.queries.GetClientByUserIdQuery;
import com.spottrack.platform.profiles.domain.model.valueobjects.AdminId;
import com.spottrack.platform.profiles.domain.model.valueobjects.ClientId;
import com.spottrack.platform.profiles.domain.model.valueobjects.PhoneNumber;
import com.spottrack.platform.shared.application.result.Result;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@Profile("dev")
@Slf4j
public class DevDataSeeder {

    private static final String ADMIN_EMAIL = "seedadmin@spottrack.com";
    private static final String CLIENT_EMAIL = "seedclient@spottrack.com";
    private static final String SEED_PASSWORD = "seed123";
    private static final String SEED_DNI = "87654321";
    private static final String ADMIN_DNI = "12345678";
    private static final String SEED_GYM_NAME = "Seed Gym";
    private static final String MANUFACTURER_ID = "00000000-0000-0000-0000-000000000001";

    private final RoleCommandService roleCommandService;
    private final UserCommandService userCommandService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AdminQueryService adminQueryService;
    private final AdminCommandService adminCommandService;
    private final ClientQueryService clientQueryService;
    private final ClientCommandService clientCommandService;
    private final GymCommandService gymCommandService;
    private final EquipmentCommandService equipmentCommandService;
    private final GymPersistenceRepository gymPersistenceRepository;
    private final EquipmentPersistenceRepository equipmentPersistenceRepository;
    private final MembershipCommandService membershipCommandService;
    private final MembershipRepository membershipRepository;
    private final ActivityReportRepository activityReportRepository;
    private final ActivityReportCommandService activityReportCommandService;
    private final MaintenanceQuoteCommandService maintenanceQuoteCommandService;
    private final MaintenanceQuoteQueryService maintenanceQuoteQueryService;
    private final ROIProjectionCommandService roiProjectionCommandService;
    private final ROIProjectionQueryService roiProjectionQueryService;
    private final MaintenanceCommandService maintenanceCommandService;
    private final TechnicalTicketJpaRepository technicalTicketJpaRepository;

    public DevDataSeeder(
            RoleCommandService roleCommandService,
            UserCommandService userCommandService,
            UserRepository userRepository,
            RoleRepository roleRepository,
            AdminQueryService adminQueryService,
            AdminCommandService adminCommandService,
            ClientQueryService clientQueryService,
            ClientCommandService clientCommandService,
            GymCommandService gymCommandService,
            EquipmentCommandService equipmentCommandService,
            GymPersistenceRepository gymPersistenceRepository,
            EquipmentPersistenceRepository equipmentPersistenceRepository,
            MembershipCommandService membershipCommandService,
            MembershipRepository membershipRepository,
            ActivityReportRepository activityReportRepository,
            ActivityReportCommandService activityReportCommandService,
            MaintenanceQuoteCommandService maintenanceQuoteCommandService,
            MaintenanceQuoteQueryService maintenanceQuoteQueryService,
            ROIProjectionCommandService roiProjectionCommandService,
            ROIProjectionQueryService roiProjectionQueryService,
            MaintenanceCommandService maintenanceCommandService,
            TechnicalTicketJpaRepository technicalTicketJpaRepository) {
        this.roleCommandService = roleCommandService;
        this.userCommandService = userCommandService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.adminQueryService = adminQueryService;
        this.adminCommandService = adminCommandService;
        this.clientQueryService = clientQueryService;
        this.clientCommandService = clientCommandService;
        this.gymCommandService = gymCommandService;
        this.equipmentCommandService = equipmentCommandService;
        this.gymPersistenceRepository = gymPersistenceRepository;
        this.equipmentPersistenceRepository = equipmentPersistenceRepository;
        this.membershipCommandService = membershipCommandService;
        this.membershipRepository = membershipRepository;
        this.activityReportRepository = activityReportRepository;
        this.activityReportCommandService = activityReportCommandService;
        this.maintenanceQuoteCommandService = maintenanceQuoteCommandService;
        this.maintenanceQuoteQueryService = maintenanceQuoteQueryService;
        this.roiProjectionCommandService = roiProjectionCommandService;
        this.roiProjectionQueryService = roiProjectionQueryService;
        this.maintenanceCommandService = maintenanceCommandService;
        this.technicalTicketJpaRepository = technicalTicketJpaRepository;
    }

    private record GymSeedResult(String gymId, String equipmentId) {}

    @EventListener(ApplicationReadyEvent.class)
    @Order(10)
    public void seed(ApplicationReadyEvent event) {
        log.info("[DevDataSeeder] Starting dev seed...");

        roleCommandService.handle(new SeedRolesCommand());

        var adminUserId = seedAdminUser();
        var gymSeed = seedGym(adminUserId);
        seedMembership(adminUserId);
        seedWhitelist(gymSeed.gymId());
        seedClientUser(gymSeed.gymId());
        seedActivityReport(gymSeed.equipmentId());
        seedMaintenanceQuote(gymSeed.equipmentId());
        seedRoiProjection();
        seedMaintenanceLog(gymSeed.equipmentId());

        log.info("[DevDataSeeder] Dev seed complete.");
    }

    private Long seedAdminUser() {
        if (userRepository.existsByUsername(ADMIN_EMAIL)) {
            log.info("[DevDataSeeder] Admin user already exists, skipping creation.");
            var user = userRepository.findByUsername(ADMIN_EMAIL).orElseThrow();
            return user.getId();
        }

        var adminRole = roleRepository.findByName(Roles.ROLE_ADMIN)
                .orElseGet(() -> new Role(Roles.ROLE_ADMIN));
        var result = userCommandService.handle(new SignUpCommand(ADMIN_EMAIL, SEED_PASSWORD, List.of(adminRole)));
        if (result instanceof Result.Failure<?, ?> f) {
            log.error("[DevDataSeeder] Failed to create admin user: {}", f.error());
            throw new IllegalStateException("Dev seed failed at admin user creation");
        }
        var adminUserId = ((Result.Success<com.spottrack.platform.iam.domain.model.aggregates.User, ?>) result).value().getId();
        log.info("[DevDataSeeder] Admin user created, userId={}", adminUserId);

        var admin = adminQueryService.handle(new GetAdminByUserIdQuery(adminUserId))
                .orElseThrow(() -> new IllegalStateException("Admin profile not found after sign-up"));
        if (!admin.isProfileComplete()) {
            adminCommandService.handle(new UpdateAdminProfileCommand(
                    new AdminId(admin.getId()),
                    "Seed",
                    "Admin",
                    new PhoneNumber("999111111"),
                    new com.spottrack.platform.profiles.domain.model.valueobjects.Dni(ADMIN_DNI)
            ));
            log.info("[DevDataSeeder] Admin profile updated.");
        }
        return adminUserId;
    }

    private GymSeedResult seedGym(Long adminUserId) {
        var existing = gymPersistenceRepository.findByAdminUserId(adminUserId);
        if (!existing.isEmpty()) {
            var gymId = existing.get(0).getGymId();
            var equipmentId = equipmentPersistenceRepository.findByEquipmentName("Cinta Seed")
                    .map(e -> e.getEquipmentId()).orElse(null);
            log.info("[DevDataSeeder] Gym already exists gymId={}, skipping creation.", gymId);
            return new GymSeedResult(gymId, equipmentId);
        }

        var gymResult = gymCommandService.handle(new CreateGym(SEED_GYM_NAME, adminUserId));
        if (gymResult instanceof Result.Failure<?, ?> f) {
            log.error("[DevDataSeeder] Failed to create gym: {}", f.error());
            throw new IllegalStateException("Dev seed failed at gym creation");
        }
        var gym = ((Result.Success<com.spottrack.platform.gym.domain.model.aggregates.Gym, ?>) gymResult).value();
        var gymId = gym.getId().uuid();
        log.info("[DevDataSeeder] Gym created gymId={}", gymId);

        var branchResult = gymCommandService.handle(new AddBranchCommand(gymId, "Sede Central", "Av. Seed 123"));
        if (branchResult instanceof Result.Failure<?, ?> f) {
            log.error("[DevDataSeeder] Failed to create branch: {}", f.error());
            throw new IllegalStateException("Dev seed failed at branch creation");
        }
        var branch = ((Result.Success<com.spottrack.platform.gym.domain.model.entities.Branch, ?>) branchResult).value();
        var branchId = branch.getId().uuid();
        log.info("[DevDataSeeder] Branch created branchId={}", branchId);

        var zoneResult = gymCommandService.handle(new AddZoneCommand("Zona A", 20, new BranchId(branchId)));
        if (zoneResult instanceof Result.Failure<?, ?> f) {
            log.error("[DevDataSeeder] Failed to create zone: {}", f.error());
            throw new IllegalStateException("Dev seed failed at zone creation");
        }
        var zone = ((Result.Success<com.spottrack.platform.gym.domain.model.entities.Zone, ?>) zoneResult).value();
        var zoneId = zone.getId().uuid();
        log.info("[DevDataSeeder] Zone created zoneId={}", zoneId);

        var equipResult = equipmentCommandService.handle(new RegisterEquipment(
                "Cinta Seed",
                EquipmentStatus.AVAILABLE,
                "Model-X",
                new ManufacturerId(MANUFACTURER_ID),
                new ZoneId(zoneId),
                new Money(BigDecimal.valueOf(500), "USD")
        ));
        if (equipResult instanceof Result.Failure<?, ?> f) {
            log.error("[DevDataSeeder] Failed to create equipment: {}", f.error());
            throw new IllegalStateException("Dev seed failed at equipment creation");
        }
        var equipmentId = ((Result.Success<com.spottrack.platform.gym.domain.model.aggregates.Equipment, ?>) equipResult).value().getId().uuid();
        log.info("[DevDataSeeder] Equipment created equipmentId={}", equipmentId);

        return new GymSeedResult(gymId, equipmentId);
    }

    private void seedMembership(Long adminUserId) {
        var memberships = membershipRepository.findByClientId(adminUserId);
        boolean hasActive = memberships.stream()
                .anyMatch(m -> m.getStatus() == MembershipStatus.ACTIVE);
        if (hasActive) {
            log.info("[DevDataSeeder] Active membership already exists, skipping.");
            return;
        }

        var today = LocalDate.now();
        var createResult = membershipCommandService.handle(new CreateMembershipCommand(
                adminUserId,
                MembershipTier.PLATINUM,
                MembershipTier.PLATINUM.toMoney(),
                today,
                today.plusDays(30)
        ));
        if (createResult instanceof Result.Failure<?, ?> f) {
            log.error("[DevDataSeeder] Failed to create membership: {}", f.error());
            throw new IllegalStateException("Dev seed failed at membership creation");
        }
        var membership = ((Result.Success<com.spottrack.platform.membership.domain.model.aggregates.Membership, ?>) createResult).value();

        var activateResult = membershipCommandService.handle(new ActivateMembershipCommand(membership.getMembershipId()));
        if (activateResult instanceof Result.Failure<?, ?> f) {
            log.error("[DevDataSeeder] Failed to activate membership: {}", f.error());
            throw new IllegalStateException("Dev seed failed at membership activation");
        }
        log.info("[DevDataSeeder] Membership created and activated.");
    }

    private void seedWhitelist(String gymId) {
        var dniResult = gymCommandService.handle(new AddDniToWhitelistCommand(
                gymId,
                new com.spottrack.platform.gym.domain.model.valueobjects.Dni(SEED_DNI)
        ));
        if (dniResult instanceof Result.Failure<?, ?> f) {
            log.info("[DevDataSeeder] DNI {} already in whitelist ({}), skipping.", SEED_DNI, f.error());
        } else {
            log.info("[DevDataSeeder] DNI {} added to whitelist.", SEED_DNI);
        }
    }

    private void seedClientUser(String gymId) {
        Long clientUserId;
        if (userRepository.existsByUsername(CLIENT_EMAIL)) {
            log.info("[DevDataSeeder] Client user already exists, skipping creation.");
            clientUserId = userRepository.findByUsername(CLIENT_EMAIL).orElseThrow().getId();
        } else {
            var clientRole = roleRepository.findByName(Roles.ROLE_CLIENT)
                    .orElseGet(() -> new Role(Roles.ROLE_CLIENT));
            var result = userCommandService.handle(new SignUpCommand(CLIENT_EMAIL, SEED_PASSWORD, List.of(clientRole)));
            if (result instanceof Result.Failure<?, ?> f) {
                log.error("[DevDataSeeder] Failed to create client user: {}", f.error());
                throw new IllegalStateException("Dev seed failed at client user creation");
            }
            clientUserId = ((Result.Success<com.spottrack.platform.iam.domain.model.aggregates.User, ?>) result).value().getId();
            log.info("[DevDataSeeder] Client user created, userId={}", clientUserId);
        }

        var client = clientQueryService.handle(new GetClientByUserIdQuery(clientUserId))
                .orElseThrow(() -> new IllegalStateException("Client profile not found after sign-up"));

        if (!client.isProfileComplete()) {
            clientCommandService.handle(new UpdateClientProfileCommand(
                    new ClientId(client.getId()),
                    "Seed",
                    "Client",
                    new PhoneNumber("999222222"),
                    new com.spottrack.platform.profiles.domain.model.valueobjects.Dni(SEED_DNI)
            ));
            log.info("[DevDataSeeder] Client profile updated.");
        }

        var assocResult = clientCommandService.handle(
                new AssociateClientWithGymCommand(client.getId(), gymId));
        if (assocResult instanceof Result.Failure<?, ?> f) {
            log.info("[DevDataSeeder] Client-gym association already exists ({}), skipping.", f.error());
        } else {
            log.info("[DevDataSeeder] Client associated with gym gymId={}.", gymId);
        }
    }

    private void seedActivityReport(String equipmentId) {
        if (equipmentId == null) {
            log.warn("[DevDataSeeder] Equipment ID not available, skipping activity report seeding.");
            return;
        }
        if (activityReportRepository.findByEquipmentId(equipmentId).isPresent()) {
            log.info("[DevDataSeeder] Activity report already exists for equipment {}, skipping.", equipmentId);
            return;
        }
        activityReportCommandService.handle(new RequestActivityAnalysisCommand(
                equipmentId, 45, 10, "Cinta atascada", 8.0));
        log.info("[DevDataSeeder] Activity report seeded for equipment {}.", equipmentId);
    }

    private void seedMaintenanceQuote(String equipmentId) {
        if (!maintenanceQuoteQueryService.handle(new GetAllMaintenanceQuotesQuery()).isEmpty()) {
            log.info("[DevDataSeeder] Maintenance quote already seeded, skipping.");
            return;
        }
        var quoteResult = maintenanceQuoteCommandService.handle(new RequestADetailedMaintenanceQuoteCommand(
                equipmentId, 120.0, "USD", "CORRECTIVE", "n/a", 1, 0.0));
        if (quoteResult.isEmpty()) {
            log.warn("[DevDataSeeder] Failed to seed maintenance quote.");
            return;
        }
        var quoteId = quoteResult.get().getId();
        maintenanceQuoteCommandService.handle(quoteId, new RequestSparePartsCommand("Belt", 3, 25.0));
        maintenanceQuoteCommandService.handle(quoteId, new RequestPreventiveCostCommand(40.0, "USD"));
        log.info("[DevDataSeeder] Maintenance quote seeded, id={}.", quoteId);
    }

    private void seedRoiProjection() {
        if (!roiProjectionQueryService.handle(new GetAllROIProjectionsQuery()).isEmpty()) {
            log.info("[DevDataSeeder] ROI projection already seeded, skipping.");
            return;
        }
        var roiResult = roiProjectionCommandService.handle(new RequestRoiCommand(12.5));
        if (roiResult.isEmpty()) {
            log.warn("[DevDataSeeder] Failed to seed ROI projection.");
            return;
        }
        var roiId = roiResult.get().getId();
        roiProjectionCommandService.handle(roiId, new RequestDowntimeCostCommand(20, "sensor jam"));
        roiProjectionCommandService.handle(roiId, new RequestEarningsCommand(600.0));
        log.info("[DevDataSeeder] ROI projection seeded, id={}.", roiId);
    }

    private void seedMaintenanceLog(String equipmentId) {
        if (equipmentId == null) {
            log.warn("[DevDataSeeder] Equipment ID not available, skipping maintenance log seeding.");
            return;
        }
        boolean alreadyExists = technicalTicketJpaRepository.findAll().stream()
                .anyMatch(t -> equipmentId.equals(t.getEquipmentId()));
        if (alreadyExists) {
            log.info("[DevDataSeeder] Maintenance ticket already exists for equipment {}, skipping.", equipmentId);
            return;
        }
        var maintenanceResult = maintenanceCommandService.handle(new RequestMaintenance(
                new EquipmentId(equipmentId), "SYSTEM", "Rutina de mantenimiento preventivo"));
        if (maintenanceResult instanceof Result.Failure<?, ?> f) {
            log.error("[DevDataSeeder] Failed to request maintenance: {}", f.error());
            return;
        }
        var maintenanceId = ((Result.Success<com.spottrack.platform.maintenance.domain.model.aggregates.Maintenance, ?>) maintenanceResult).value().getId().uuid();

        var ticketResult = maintenanceCommandService.handle(new CreateTechnicalTicketCommand(
                maintenanceId,
                TicketPriority.LOW,
                TicketType.PREVENTIVE
        ));
        if (ticketResult instanceof Result.Failure<?, ?> f) {
            log.error("[DevDataSeeder] Failed to create maintenance ticket: {}", f.error());
            return;
        }
        var ticket = ((Result.Success<com.spottrack.platform.maintenance.domain.model.aggregates.TechnicalTicket, ?>) ticketResult).value();

        var completionResult = maintenanceCommandService.handle(new RegisterMaintenanceCompletion(
                new TechnicalTicketId(ticket.getTicketId().uuid()),
                new MaintenanceId(ticket.getMaintenanceId()),
                "Lubricación y ajuste de banda completados",
                BigDecimal.valueOf(35.00)
        ));
        if (completionResult instanceof Result.Failure<?, ?> f) {
            log.error("[DevDataSeeder] Failed to register maintenance completion: {}", f.error());
            return;
        }
        log.info("[DevDataSeeder] Maintenance ticket and completion log seeded for equipment {}.", equipmentId);
    }
}
