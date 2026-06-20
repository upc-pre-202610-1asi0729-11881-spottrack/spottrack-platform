package com.spottrack.platform.analytics.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.analytics.domain.model.aggregates.ActivityReport;

public class ActivityReportFromEntityAssembler {
    public static ActivityReport toEntityFromFields(ActivityReport entity) {
        return entity; // Como tu Agregado ya tiene JPA, el mapeo es directo de la misma entidad
    }
}
