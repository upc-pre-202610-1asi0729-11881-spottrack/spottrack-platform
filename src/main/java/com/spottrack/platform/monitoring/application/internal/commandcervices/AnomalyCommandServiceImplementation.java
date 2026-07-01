package com.spottrack.platform.monitoring.application.internal.commandcervices;

import com.spottrack.platform.monitoring.application.commandServices.AnomalyCommandService;
import com.spottrack.platform.monitoring.domain.model.aggregates.Anomaly;
import com.spottrack.platform.monitoring.domain.model.commands.ReportAnomalyCommand;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class AnomalyCommandServiceImplementation implements AnomalyCommandService {
    @Override
    public Result<Anomaly, ApplicationError> handle(ReportAnomalyCommand command) {
        return null;
    }
}
