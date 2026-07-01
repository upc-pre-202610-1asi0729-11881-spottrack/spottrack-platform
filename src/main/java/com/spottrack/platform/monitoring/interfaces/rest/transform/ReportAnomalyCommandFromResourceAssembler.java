package com.spottrack.platform.monitoring.interfaces.rest.transform;

import com.spottrack.platform.monitoring.domain.model.commands.ReportAnomalyManuallyCommand;
import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyType;
import com.spottrack.platform.monitoring.domain.model.valueobjects.SessionTrackerId;
import com.spottrack.platform.monitoring.interfaces.rest.resources.ReportAnomalyResource;

public class ReportAnomalyCommandFromResourceAssembler {
    private ReportAnomalyCommandFromResourceAssembler() {}

    public static ReportAnomalyManuallyCommand toCommandFromResource(ReportAnomalyResource resource) {
        return new ReportAnomalyManuallyCommand(
                new SessionTrackerId(resource.sessionTrackerId()),
                AnomalyType.valueOf(resource.anomalyType()),
                resource.description()
        );
    }
}
