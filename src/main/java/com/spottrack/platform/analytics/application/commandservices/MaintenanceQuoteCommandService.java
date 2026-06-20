package com.spottrack.platform.analytics.application.commandservices;

import com.spottrack.platform.analytics.domain.model.aggregates.MaintenanceQuote;
import com.spottrack.platform.analytics.domain.model.commands.RequestADetailedMaintenanceQuoteCommand;

import java.util.Optional;

public interface MaintenanceQuoteCommandService {
    Optional<MaintenanceQuote> handle(RequestADetailedMaintenanceQuoteCommand command);
}
