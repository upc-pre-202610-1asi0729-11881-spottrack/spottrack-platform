package com.spottrack.platform.analytics.application.internal.commandservices;

import com.spottrack.platform.analytics.domain.model.aggregates.MaintenanceQuote;
import com.spottrack.platform.analytics.domain.model.valueobjects.MaintenanceQuoteId;
import com.spottrack.platform.analytics.domain.repositories.MaintenanceQuoteRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MaintenanceQuoteCommandServiceImpl {

    private final MaintenanceQuoteRepository maintenanceQuoteRepository;

    public MaintenanceQuoteCommandServiceImpl(MaintenanceQuoteRepository maintenanceQuoteRepository) {
        this.maintenanceQuoteRepository = maintenanceQuoteRepository;
    }

    // Método para procesar la creación de una cotización de mantenimiento
    public Optional<MaintenanceQuote> handle(Long id) {
        var maintenanceQuoteId = new MaintenanceQuoteId(ThreadLocalRandom.current().nextLong(1000, 100000));
        var maintenanceQuote = new MaintenanceQuote(maintenanceQuoteId);

        this.maintenanceQuoteRepository.save(maintenanceQuote);
        return Optional.of(maintenanceQuote);
    }
}
