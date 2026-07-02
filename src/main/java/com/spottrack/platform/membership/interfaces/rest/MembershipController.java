package com.spottrack.platform.membership.interfaces.rest;

import com.spottrack.platform.membership.application.commandservices.MembershipCommandService;
import com.spottrack.platform.membership.application.commandservices.PaymentCommandService;
import com.spottrack.platform.membership.application.queryservices.MembershipQueryService;
import com.spottrack.platform.membership.domain.model.commands.CancelMembershipCommand;
import com.spottrack.platform.membership.domain.model.commands.InitiateDebtPaymentCommand;
import com.spottrack.platform.membership.domain.model.queries.GetMembershipByIdQuery;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipId;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipStatus;
import com.spottrack.platform.membership.interfaces.rest.resources.CreateMembershipResource;
import com.spottrack.platform.membership.interfaces.rest.transform.CreateMembershipCommandFromResourceAssembler;
import com.spottrack.platform.membership.interfaces.rest.transform.MembershipResourceFromEntityAssembler;
import com.spottrack.platform.profiles.interfaces.acl.ProfilesContextFacade;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.spottrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/memberships")
public class MembershipController {

    private final MembershipCommandService membershipCommandService;
    private final MembershipQueryService membershipQueryService;
    private final PaymentCommandService paymentCommandService;
    private final ProfilesContextFacade profilesContextFacade;

    public MembershipController(
            MembershipCommandService membershipCommandService,
            MembershipQueryService membershipQueryService,
            PaymentCommandService paymentCommandService,
            ProfilesContextFacade profilesContextFacade) {
        this.membershipCommandService = membershipCommandService;
        this.membershipQueryService = membershipQueryService;
        this.paymentCommandService = paymentCommandService;
        this.profilesContextFacade = profilesContextFacade;
    }

    @PostMapping
    @Schema(description = "Create a new membership")
    public ResponseEntity<?> createMembership(@RequestBody @Valid CreateMembershipResource resource) {
        var command = CreateMembershipCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = membershipCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MembershipResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{membershipId}/cancel")
    @Schema(description = "Request cancellation of a membership at the end of the current billing period")
    public ResponseEntity<?> cancelMembership(
            Authentication authentication,
            @PathVariable UUID membershipId) {
        var clientId = resolveClientId(authentication);
        if (clientId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Client", authentication.getName()));
        }
        var membership = membershipQueryService.handle(new GetMembershipByIdQuery(new MembershipId(membershipId)));
        if (membership.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Membership", membershipId.toString()));
        }
        var ownershipError = checkOwnership(membership.get().getClientId(), clientId, membershipId.toString());
        if (ownershipError.isPresent()) return ownershipError.get();
        var command = new CancelMembershipCommand(new MembershipId(membershipId));
        var result = membershipCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MembershipResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PostMapping("/{membershipId}/pay-debt")
    @Schema(description = "Initiate a debt regularization payment for a suspended membership")
    public ResponseEntity<?> payDebt(
            Authentication authentication,
            @PathVariable UUID membershipId) {
        var clientId = resolveClientId(authentication);
        if (clientId == 0L) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Client", authentication.getName()));
        }
        var membership = membershipQueryService.handle(new GetMembershipByIdQuery(new MembershipId(membershipId)));
        if (membership.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("Membership", membershipId.toString()));
        }
        var ownershipError = checkOwnership(membership.get().getClientId(), clientId, membershipId.toString());
        if (ownershipError.isPresent()) return ownershipError.get();
        if (membership.get().getStatus() != MembershipStatus.SUSPENDED) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.businessRuleViolation("Membership.payDebt",
                            "membership.error.payDebt.notSuspended"));
        }
        var tier = membership.get().getMembershipTier();
        var command = new InitiateDebtPaymentCommand(membershipId, tier, tier.toMoney());
        var result = paymentCommandService.handle(command);
        return switch (result) {
            case com.spottrack.platform.shared.application.result.Result.Success<String, ?> s ->
                    ResponseEntity.ok(Map.of("checkoutUrl", s.value()));
            case com.spottrack.platform.shared.application.result.Result.Failure<?, ApplicationError> f ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(f.error());
        };
    }

    private Long resolveClientId(Authentication authentication) {
        return profilesContextFacade.fetchClientIdByEmail(authentication.getName());
    }

    private Optional<ResponseEntity<?>> checkOwnership(Long membershipClientId, Long callerClientId, String membershipId) {
        if (!membershipClientId.equals(callerClientId)) {
            return Optional.of(ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.forbidden("Membership", "membershipId:" + membershipId)));
        }
        return Optional.empty();
    }
}
