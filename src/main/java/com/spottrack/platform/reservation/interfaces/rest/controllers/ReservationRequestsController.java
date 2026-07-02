package com.spottrack.platform.reservation.interfaces.rest.controllers;

import com.spottrack.platform.iam.interfaces.acl.IamContextFacade;
import com.spottrack.platform.membership.interfaces.acl.MembershipContextFacade;
import com.spottrack.platform.profiles.interfaces.acl.ProfilesContextFacade;
import com.spottrack.platform.reservation.application.commandServices.ReservationRequestCommandService;
import com.spottrack.platform.reservation.application.queryservices.ReservationRequestQueryService;
import com.spottrack.platform.reservation.domain.model.aggregates.ReservationRequest;
import com.spottrack.platform.reservation.domain.model.commands.RequestEquipmentStatusChangeToAvailable;
import com.spottrack.platform.reservation.domain.model.queries.GetReservationRequestByUuidQuery;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestId;
import com.spottrack.platform.reservation.interfaces.rest.resources.RequestAlternativeEquipmentResource;
import com.spottrack.platform.reservation.interfaces.rest.resources.SubmitRequestOccupyEquipmentResource;
import com.spottrack.platform.reservation.interfaces.rest.transform.RequestAlternativeEquipmentCommandFromResourceAssembler;
import com.spottrack.platform.reservation.interfaces.rest.transform.ReservationRequestResourceFromEntityAssembler;
import com.spottrack.platform.reservation.interfaces.rest.transform.SubmitRequestOccupyEquipmentCommandFromResourceAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import com.spottrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservation-requests")
@Tag(name = "Reservation Requests")
public class ReservationRequestsController {

    private final ReservationRequestCommandService commandService;
    private final ReservationRequestQueryService queryService;
    private final IamContextFacade iamContextFacade;
    private final ProfilesContextFacade profilesContextFacade;
    private final MembershipContextFacade membershipContextFacade;

    public ReservationRequestsController(
            ReservationRequestCommandService commandService,
            ReservationRequestQueryService queryService,
            IamContextFacade iamContextFacade,
            ProfilesContextFacade profilesContextFacade,
            MembershipContextFacade membershipContextFacade) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.iamContextFacade = iamContextFacade;
        this.profilesContextFacade = profilesContextFacade;
        this.membershipContextFacade = membershipContextFacade;
    }

    @PostMapping
    public ResponseEntity<?> submitRequest(
            Authentication authentication,
            @RequestBody SubmitRequestOccupyEquipmentResource resource) {
        var clientId = resolveClientId(authentication);
        if (clientId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Client", authentication.getName()));
        }
        var membershipError = checkMembershipAccess(clientId);
        if (membershipError.isPresent()) return membershipError.get();
        var command = SubmitRequestOccupyEquipmentCommandFromResourceAssembler.toCommandFromResource(resource, clientId);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<ReservationRequest, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(ReservationRequestResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<ReservationRequest, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    @PatchMapping("/{id}/alternative")
    public ResponseEntity<?> requestAlternative(
            Authentication authentication,
            @PathVariable String id,
            @RequestBody RequestAlternativeEquipmentResource resource) {
        var clientId = resolveClientId(authentication);
        if (clientId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Client", authentication.getName()));
        }
        var membershipError = checkMembershipAccess(clientId);
        if (membershipError.isPresent()) return membershipError.get();
        var request = queryService.handle(new GetReservationRequestByUuidQuery(id));
        if (request.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("ReservationRequest", id));
        }
        var ownershipError = checkOwnership(request.get().getClientId().clientId(), clientId, id);
        if (ownershipError.isPresent()) return ownershipError.get();
        var command = RequestAlternativeEquipmentCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<ReservationRequest, ApplicationError> s ->
                    ResponseEntity.ok(ReservationRequestResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<ReservationRequest, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    @PatchMapping("/{id}/release")
    public ResponseEntity<?> releaseEquipment(Authentication authentication, @PathVariable String id) {
        var clientId = resolveClientId(authentication);
        if (clientId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Client", authentication.getName()));
        }
        var request = queryService.handle(new GetReservationRequestByUuidQuery(id));
        if (request.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("ReservationRequest", id));
        }
        var ownershipError = checkOwnership(request.get().getClientId().clientId(), clientId, id);
        if (ownershipError.isPresent()) return ownershipError.get();
        var command = new RequestEquipmentStatusChangeToAvailable(new ReservationRequestId(id));
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<ReservationRequest, ApplicationError> s ->
                    ResponseEntity.ok(ReservationRequestResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<ReservationRequest, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    private Optional<ResponseEntity<?>> checkMembershipAccess(Long clientId) {
        var accessStatus = membershipContextFacade.fetchMembershipAccessStatus(clientId);
        if ("ACTIVE".equals(accessStatus)) return Optional.empty();
        var errorCode = "SUSPENDED".equals(accessStatus)
                ? "membership.error.access.suspended"
                : "membership.error.access.inactive";
        return Optional.of(ErrorResponseAssembler.toErrorResponseFromApplicationError(
                ApplicationError.forbidden("Membership", errorCode)));
    }

    private Long resolveClientId(Authentication authentication) {
        return profilesContextFacade.fetchClientIdByEmail(authentication.getName());
    }

    private Optional<ResponseEntity<?>> checkOwnership(Long requestClientId, Long callerClientId, String requestUuid) {
        if (!requestClientId.equals(callerClientId)) {
            return Optional.of(ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.forbidden("ReservationRequest", "requestId:" + requestUuid)));
        }
        return Optional.empty();
    }
}
