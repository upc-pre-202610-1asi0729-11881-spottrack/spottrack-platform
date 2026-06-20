package com.spottrack.platform.analytics.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.analytics.domain.model.aggregates.ROIProjection;

public class ROIProjectionFromEntityAssembler {
    public static ROIProjection toEntityFromFields(ROIProjection entity) {
        return entity;
    }
}
