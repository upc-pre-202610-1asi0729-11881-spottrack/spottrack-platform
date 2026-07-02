package com.spottrack.platform.analytics.application.internal.commandservices;

import com.spottrack.platform.analytics.application.commandservices.ROIProjectionCommandService;
import com.spottrack.platform.analytics.domain.model.aggregates.ROIProjection;
import com.spottrack.platform.analytics.domain.model.commands.RequestRoiCommand;
import com.spottrack.platform.analytics.domain.model.valueobjects.ROIProjectionId;
import com.spottrack.platform.analytics.domain.repositories.ROIProjectionRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ROIProjectionCommandServiceImpl implements ROIProjectionCommandService {

    private final ROIProjectionRepository roiProjectionRepository;

    public ROIProjectionCommandServiceImpl(ROIProjectionRepository roiProjectionRepository) {
        this.roiProjectionRepository = roiProjectionRepository;
    }

    public Optional<ROIProjection> handle(RequestRoiCommand command) {
        var roiProjectionId = new ROIProjectionId(
            ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE));
        var roiProjection = new ROIProjection(roiProjectionId);
        this.roiProjectionRepository.save(roiProjection);
        return Optional.of(roiProjection);
    }
}
