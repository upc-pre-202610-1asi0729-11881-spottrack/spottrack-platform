package com.spottrack.platform.analytics.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.analytics.domain.model.aggregates.ROIProjection;
import com.spottrack.platform.analytics.domain.model.valueobjects.ROIProjectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaROIProjectionRepository extends JpaRepository<ROIProjection, Long> {
    Optional<ROIProjection> findByRoiProjectionId(ROIProjectionId roiProjectionId);
}
