package com.spottrack.platform.iam.interfaces.rest;

import com.spottrack.platform.iam.application.commandservices.UserCommandService;
import com.spottrack.platform.iam.interfaces.rest.resources.SignUpResource;
import com.spottrack.platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.spottrack.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.spottrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users")
public class UsersController {

    private final UserCommandService userCommandService;

    public UsersController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
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
}
