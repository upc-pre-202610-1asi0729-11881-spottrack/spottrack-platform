package com.spottrack.platform.analytics.infrastructure.persistence.jpa.adapters;

import com.spottrack.platform.analytics.domain.model.aggregates.ROIProjection;
import com.spottrack.platform.analytics.domain.model.valueobjects.ROIProjectionId;
import com.spottrack.platform.analytics.domain.repositories.ROIProjectionRepository;
import com.spottrack.platform.analytics.infrastructure.persistence.jpa.repositories.JpaROIProjectionRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class ROIProjectionRepositoryImpl implements ROIProjectionRepository {

    private final JpaROIProjectionRepository jpaROIProjectionRepository;

    public ROIProjectionRepositoryImpl(JpaROIProjectionRepository jpaROIProjectionRepository) {
        this.jpaROIProjectionRepository = jpaROIProjectionRepository;
    }

    @Override
    public ROIProjection save(ROIProjection roiProjection) {
        return this.jpaROIProjectionRepository.save(roiProjection);
    }

    @Override
    public Optional<ROIProjection> findByRoiProjectionId(ROIProjectionId roiProjectionId) {
        return this.jpaROIProjectionRepository.findByRoiProjectionId(roiProjectionId);
    }
}
