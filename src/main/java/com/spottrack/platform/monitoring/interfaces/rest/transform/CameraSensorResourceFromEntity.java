package com.spottrack.platform.monitoring.interfaces.rest.transform;

import com.spottrack.platform.monitoring.domain.model.aggregates.CameraSensor;
import com.spottrack.platform.monitoring.interfaces.rest.resources.CameraSensorResource;

public class CameraSensorResourceFromEntity {
    public static CameraSensorResource toResourceFromEntity(CameraSensor entity) {
        return new CameraSensorResource(
                entity.getId(),
                entity.getCameraSensorId().uuid(),
                entity.getZoneId().uuid(),
                entity.getRegisteredAt()
        );
    }
}
