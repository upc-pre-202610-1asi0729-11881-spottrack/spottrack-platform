package com.spottrack.platform.analytics.interfaces.rest;

import com.spottrack.platform.analytics.application.commandservices.MaintenanceQuoteCommandService;
import com.spottrack.platform.analytics.domain.model.commands.RequestADetailedMaintenanceQuoteCommand;
import com.spottrack.platform.analytics.interfaces.rest.resources.MaintenanceQuoteResource;
import com.spottrack.platform.analytics.interfaces.rest.transform.MaintenanceQuoteResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/maintenance-quotes", produces = MediaType.APPLICATION_JSON_VALUE)
public class MaintenanceQuotesController {
    private final MaintenanceQuoteCommandService maintenanceQuoteCommandService;

    public MaintenanceQuotesController(MaintenanceQuoteCommandService maintenanceQuoteCommandService) {
        this.maintenanceQuoteCommandService = maintenanceQuoteCommandService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MaintenanceQuoteResource> createQuote(@RequestBody RequestADetailedMaintenanceQuoteCommand command) {
        var quote = maintenanceQuoteCommandService.handle(command);
        return quote.map(value -> new ResponseEntity<>(MaintenanceQuoteResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
