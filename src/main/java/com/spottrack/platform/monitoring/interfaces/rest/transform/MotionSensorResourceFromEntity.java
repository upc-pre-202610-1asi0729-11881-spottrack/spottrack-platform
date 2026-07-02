package com.spottrack.platform.monitoring.interfaces.rest.transform;

import com.spottrack.platform.monitoring.domain.model.aggregates.MotionSensor;
import com.spottrack.platform.monitoring.interfaces.rest.resources.MotionSensorResource;

public class MotionSensorResourceFromEntity {
    public static MotionSensorResource toResourceFromEntity(MotionSensor entity) {
        return new MotionSensorResource(
                entity.getId(),
                entity.getMotionSensorId().uuid(),
                entity.getEquipmentId().uuid(),
                entity.getRegisteredAt()
        );
    }
}
