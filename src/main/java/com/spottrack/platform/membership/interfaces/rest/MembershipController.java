package com.spottrack.platform.membership.interfaces.rest;

import com.spottrack.platform.membership.application.commandservices.MembershipCommandService;
import com.spottrack.platform.membership.interfaces.rest.resources.CreateMembershipResource;
import com.spottrack.platform.membership.interfaces.rest.transform.CreateMembershipCommandFromResourceAssembler;
import com.spottrack.platform.membership.interfaces.rest.transform.MembershipResourceFromEntityAssembler;
import com.spottrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/memberships")
public class MembershipController {

    private final MembershipCommandService membershipCommandService;

    public MembershipController(MembershipCommandService membershipCommandService) {
        this.membershipCommandService = membershipCommandService;
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
}
