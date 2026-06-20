package com.spottrack.platform.analytics.application.commandservices;

import com.spottrack.platform.analytics.domain.model.aggregates.ROIProjection;
import com.spottrack.platform.analytics.domain.model.commands.RequestRoiCommand;

import java.util.Optional;

public interface ROIProjectionCommandService {
    Optional<ROIProjection> handle(RequestRoiCommand command);
}
