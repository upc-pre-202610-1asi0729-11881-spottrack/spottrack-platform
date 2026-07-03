package com.spottrack.platform.gym.interfaces.rest.controllers;

import com.spottrack.platform.gym.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.gym.application.queryservices.EquipmentQueryService;
import com.spottrack.platform.gym.application.queryservices.GymQueryService;
import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.queries.GetEquipmentById;
import com.spottrack.platform.gym.domain.model.queries.GetGymById;
import com.spottrack.platform.gym.domain.model.queries.GetGymsByAdminUserId;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.gym.domain.model.valueobjects.GymId;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.BranchPersistenceRepository;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.EquipmentPersistenceRepository;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.ZonePersistenceRepository;
import com.spottrack.platform.gym.interfaces.rest.resources.*;
import com.spottrack.platform.gym.interfaces.rest.transform.*;
import com.spottrack.platform.iam.interfaces.acl.IamContextFacade;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import com.spottrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/equipments")
@Tag(name = "Equipments")
public class EquipmentsController {

    private final EquipmentCommandService commandService;
    private final EquipmentQueryService equipmentQueryService;
    private final GymQueryService gymQueryService;
    private final IamContextFacade iamContextFacade;
    private final ZonePersistenceRepository zonePersistenceRepository;
    private final BranchPersistenceRepository branchPersistenceRepository;
    private final EquipmentPersistenceRepository equipmentPersistenceRepository;

    public EquipmentsController(EquipmentCommandService commandService,
                                EquipmentQueryService queryService,
                                GymQueryService gymQueryService,
                                IamContextFacade iamContextFacade,
                                ZonePersistenceRepository zonePersistenceRepository,
                                BranchPersistenceRepository branchPersistenceRepository,
                                EquipmentPersistenceRepository equipmentPersistenceRepository) {
        this.commandService = commandService;
        this.equipmentQueryService = queryService;
        this.gymQueryService = gymQueryService;
        this.iamContextFacade = iamContextFacade;
        this.zonePersistenceRepository = zonePersistenceRepository;
        this.branchPersistenceRepository = branchPersistenceRepository;
        this.equipmentPersistenceRepository = equipmentPersistenceRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerEquipment(Authentication authentication,
                                               @RequestBody RegisterEquipmentResource resource) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var zoneOwnershipError = checkZoneOwnership(resource.zoneId(), adminUserId);
        if (zoneOwnershipError.isPresent()) return zoneOwnershipError.get();
        var command = RegisterEquipmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Equipment, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @GetMapping("/{equipmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getEquipmentById(Authentication authentication,
                                              @PathVariable String equipmentId) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var ownershipError = checkEquipmentOwnership(equipmentId, adminUserId);
        if (ownershipError.isPresent()) return ownershipError.get();
        var equipment = equipmentQueryService.handle(new GetEquipmentById(new EquipmentId(equipmentId)));
        if (equipment.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(EquipmentResourceFromEntityAssembler.toResourceFromEntity(equipment.get()));
    }

    @PatchMapping("/{equipmentId}/out-of-service")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> markEquipmentOutOfService(Authentication authentication,
                                                       @PathVariable String equipmentId,
                                                       @RequestBody MarkEquipmentOutOfServiceResource resource) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var ownershipError = checkEquipmentOwnership(equipmentId, adminUserId);
        if (ownershipError.isPresent()) return ownershipError.get();
        var command = EquipmentMarkOutOfServiceFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Equipment, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.OK)
                            .body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/{equipmentId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> UpdateEquipmentStatus(Authentication authentication,
                                                   @PathVariable String equipmentId,
                                                   @RequestBody UpdateEquipmentStatusResource resource) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var ownershipError = checkEquipmentOwnership(equipmentId, adminUserId);
        if (ownershipError.isPresent()) return ownershipError.get();
        var command = UpdateEquipmentStatusCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Equipment, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.OK)
                            .body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/{equipmentId}/relocate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> relocateEquipment(Authentication authentication,
                                               @PathVariable String equipmentId,
                                               @RequestBody RelocateEquipmentResource resource) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var ownershipError = checkEquipmentOwnership(equipmentId, adminUserId);
        if (ownershipError.isPresent()) return ownershipError.get();
        var targetZoneError = checkZoneOwnership(resource.zoneId(), adminUserId);
        if (targetZoneError.isPresent()) return targetZoneError.get();
        var command = RelocateEquipmentCommandFromResourceAssembler.toCommandFromResource(equipmentId, resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Equipment, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.OK)
                            .body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/{equipmentId}/decomission")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> decomissionEquipment(Authentication authentication,
                                                  @PathVariable String equipmentId,
                                                  @RequestBody DecomissionEquipmentResource resource) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var ownershipError = checkEquipmentOwnership(equipmentId, adminUserId);
        if (ownershipError.isPresent()) return ownershipError.get();
        var command = DecomissionEquipmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Equipment, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.OK)
                            .body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/{equipmentId}/maintenance-threshold")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> defineMaintenanceThreshold(Authentication authentication,
                                                        @PathVariable String equipmentId,
                                                        @RequestBody DefineMaintenanceThresholdResource resource) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var ownershipError = checkEquipmentOwnership(equipmentId, adminUserId);
        if (ownershipError.isPresent()) return ownershipError.get();
        var command = DefineMaintenanceThresholdCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Equipment, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.OK)
                            .body(EquipmentResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Equipment, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllEquipments(Authentication authentication) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var gyms = gymQueryService.handle(new GetGymsByAdminUserId(adminUserId));
        var equipments = gyms.stream()
                .flatMap(gym -> branchPersistenceRepository.findByGymId(gym.getId().uuid()).stream())
                .flatMap(branch -> zonePersistenceRepository.findByBranchId(branch.getBranchId()).stream())
                .flatMap(zone -> equipmentPersistenceRepository.findByZoneId(zone.getZoneId()).stream())
                .map(EquipmentPersistenceAssembler::toDomainFromPersistence)
                .toList();
        return ResponseEntity.ok(equipments.stream()
                .map(EquipmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }

    private Long resolveAdminUserId(Authentication authentication) {
        return iamContextFacade.fetchUserIdByUsername(authentication.getName()).orElse(0L);
    }

    private Optional<ResponseEntity<?>> checkZoneOwnership(String zoneId, Long callerAdminUserId) {
        var zone = zonePersistenceRepository.findByZoneId(zoneId);
        if (zone.isEmpty()) return Optional.of(ResponseEntity.notFound().build());
        var branch = branchPersistenceRepository.findByBranchId(zone.get().getBranchId());
        if (branch.isEmpty()) return Optional.of(ResponseEntity.notFound().build());
        var gym = gymQueryService.handle(new GetGymById(new GymId(branch.get().getGymId())));
        if (gym.isEmpty()) return Optional.of(ResponseEntity.notFound().build());
        if (!callerAdminUserId.equals(gym.get().getAdminUserId())) {
            return Optional.of(ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.forbidden("Equipment", "zoneId:" + zoneId)));
        }
        return Optional.empty();
    }

    private Optional<ResponseEntity<?>> checkEquipmentOwnership(String equipmentId, Long callerAdminUserId) {
        var equipment = equipmentQueryService.handle(new GetEquipmentById(new EquipmentId(equipmentId)));
        if (equipment.isEmpty()) return Optional.of(ResponseEntity.notFound().build());
        var zoneId = equipment.get().getZoneId();
        if (zoneId == null) {
            return Optional.of(ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.forbidden("Equipment", "equipmentId:" + equipmentId)));
        }
        return checkZoneOwnership(zoneId.uuid(), callerAdminUserId);
    }
}
