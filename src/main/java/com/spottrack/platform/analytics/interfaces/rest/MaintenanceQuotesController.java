package com.spottrack.platform.analytics.interfaces.rest;

import com.spottrack.platform.analytics.application.internal.commandservices.MaintenanceQuoteCommandServiceImpl;
import com.spottrack.platform.analytics.interfaces.rest.resources.MaintenanceQuoteResource;
import com.spottrack.platform.analytics.interfaces.rest.transform.MaintenanceQuoteResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/maintenance-quotes")
public class MaintenanceQuotesController {
    private final MaintenanceQuoteCommandServiceImpl maintenanceQuoteCommandService;

    public MaintenanceQuotesController(MaintenanceQuoteCommandServiceImpl maintenanceQuoteCommandService) {
        this.maintenanceQuoteCommandService = maintenanceQuoteCommandService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceQuoteResource> createQuote() {
        var quote = maintenanceQuoteCommandService.handle(1L);
        return quote.map(value -> new ResponseEntity<>(MaintenanceQuoteResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
