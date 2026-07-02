package com.spottrack.platform.analytics.application.internal.commandservices;

import com.spottrack.platform.analytics.application.commandservices.MaintenanceQuoteCommandService;
import com.spottrack.platform.analytics.domain.model.aggregates.MaintenanceQuote;
import com.spottrack.platform.analytics.domain.model.commands.RequestADetailedMaintenanceQuoteCommand;
import com.spottrack.platform.analytics.domain.model.valueobjects.MaintenanceQuoteId;
import com.spottrack.platform.analytics.domain.repositories.MaintenanceQuoteRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MaintenanceQuoteCommandServiceImpl implements MaintenanceQuoteCommandService {

    private final MaintenanceQuoteRepository maintenanceQuoteRepository;

    public MaintenanceQuoteCommandServiceImpl(MaintenanceQuoteRepository maintenanceQuoteRepository) {
        this.maintenanceQuoteRepository = maintenanceQuoteRepository;
    }

    public Optional<MaintenanceQuote> handle(RequestADetailedMaintenanceQuoteCommand command) {
        var maintenanceQuoteId = new MaintenanceQuoteId(
            ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE));
        var maintenanceQuote = new MaintenanceQuote(maintenanceQuoteId);
        this.maintenanceQuoteRepository.save(maintenanceQuote);
        return Optional.of(maintenanceQuote);
    }
}
