package com.spottrack.platform.analytics.application.internal.commandservices;

import com.spottrack.platform.analytics.domain.model.aggregates.ROIProjection;
import com.spottrack.platform.analytics.domain.model.valueobjects.ROIProjectionId;
import com.spottrack.platform.analytics.domain.repositories.ROIProjectionRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ROIProjectionCommandServiceImpl {

    private final ROIProjectionRepository roiProjectionRepository;

    public ROIProjectionCommandServiceImpl(ROIProjectionRepository roiProjectionRepository) {
        this.roiProjectionRepository = roiProjectionRepository;
    }

    // Método para procesar la generación de proyecciones
    public Optional<ROIProjection> handle(Long id) {
        var roiProjectionId = new ROIProjectionId(ThreadLocalRandom.current().nextLong(1000, 100000));
        var roiProjection = new ROIProjection(roiProjectionId);

        this.roiProjectionRepository.save(roiProjection);
        return Optional.of(roiProjection);
    }
}
