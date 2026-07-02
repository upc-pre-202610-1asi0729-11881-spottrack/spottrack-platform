package com.spottrack.platform.gym.interfaces.rest.controllers;

import com.spottrack.platform.gym.application.commandServices.GymCommandService;
import com.spottrack.platform.gym.application.queryservices.GymQueryService;
import com.spottrack.platform.gym.domain.model.aggregates.Gym;
import com.spottrack.platform.gym.domain.model.entities.Branch;
import com.spottrack.platform.gym.domain.model.entities.Zone;
import com.spottrack.platform.gym.domain.model.queries.GetGymById;
import com.spottrack.platform.gym.domain.model.queries.GetGymsByAdminUserId;
import com.spottrack.platform.gym.domain.model.valueobjects.GymId;
import com.spottrack.platform.gym.interfaces.rest.resources.AddBranchResource;
import com.spottrack.platform.gym.interfaces.rest.resources.AddZoneResource;
import com.spottrack.platform.gym.interfaces.rest.resources.CreateGymResource;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/gyms")
@Tag(name = "gyms")
public class GymController {

    private final GymCommandService commandService;
    private final GymQueryService gymQueryService;
    private final IamContextFacade iamContextFacade;

    public GymController(GymCommandService commandService,
                         GymQueryService gymQueryService,
                         IamContextFacade iamContextFacade) {
        this.commandService = commandService;
        this.gymQueryService = gymQueryService;
        this.iamContextFacade = iamContextFacade;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createGym(Authentication authentication, @RequestBody CreateGymResource resource) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var command = CreateGymCommandFromResourceAssembler.toCommandFromResource(resource, adminUserId);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Gym, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED).body(GymResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Gym, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getMyGyms(Authentication authentication) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        List<Gym> gyms = gymQueryService.handle(new GetGymsByAdminUserId(adminUserId));
        var resources = gyms.stream().map(GymResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping("/{gymId}/branches")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBranch(Authentication authentication,
                                       @PathVariable String gymId,
                                       @RequestBody AddBranchResource resource) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var ownershipError = checkOwnership(gymId, adminUserId);
        if (ownershipError.isPresent()) return ownershipError.get();
        var command = AddBranchCommandFromResourceAssembler.toCommandFromResource(gymId, resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Branch, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED).body(BranchResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Branch, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PostMapping("/{gymId}/branches/{branchId}/zones")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addZone(Authentication authentication,
                                     @PathVariable String gymId,
                                     @PathVariable String branchId,
                                     @RequestBody AddZoneResource resource) {
        var adminUserId = resolveAdminUserId(authentication);
        if (adminUserId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Admin", authentication.getName()));
        }
        var ownershipError = checkOwnership(gymId, adminUserId);
        if (ownershipError.isPresent()) return ownershipError.get();
        var command = AddZoneCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Zone, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED).body(ZoneResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Zone, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    private Long resolveAdminUserId(Authentication authentication) {
        return iamContextFacade.fetchUserIdByUsername(authentication.getName()).orElse(0L);
    }

    private Optional<ResponseEntity<?>> checkOwnership(String gymId, Long callerAdminUserId) {
        var gym = gymQueryService.handle(new GetGymById(new GymId(gymId)));
        if (gym.isEmpty()) {
            return Optional.of(ResponseEntity.notFound().build());
        }
        var storedAdminUserId = gym.get().getAdminUserId();
        if (storedAdminUserId == null || !storedAdminUserId.equals(callerAdminUserId)) {
            return Optional.of(ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.forbidden("Gym", "gymId:" + gymId)));
        }
        return Optional.empty();
    }
}
