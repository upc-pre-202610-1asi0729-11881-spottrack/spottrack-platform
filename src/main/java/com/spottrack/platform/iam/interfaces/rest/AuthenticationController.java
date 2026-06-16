package com.spottrack.platform.iam.interfaces.rest;

import com.spottrack.platform.iam.application.commandservices.UserCommandService;
import com.spottrack.platform.iam.interfaces.rest.resources.ResetPasswordResource;
import com.spottrack.platform.iam.interfaces.rest.resources.SignInResource;
import com.spottrack.platform.iam.interfaces.rest.resources.SignOutResource;
import com.spottrack.platform.iam.interfaces.rest.resources.SignUpResource;
import com.spottrack.platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.spottrack.platform.iam.interfaces.rest.transform.ResetPasswordCommandFromResourceAssembler;
import com.spottrack.platform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.spottrack.platform.iam.interfaces.rest.transform.SignOutCommandFromResourceAssembler;
import com.spottrack.platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.spottrack.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.spottrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInResource resource) {
        var command = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AuthenticatedUserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpResource resource) {
        var command = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordResource resource) {
        var command = ResetPasswordCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut(@Valid @RequestBody SignOutResource resource) {
        var command = SignOutCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
