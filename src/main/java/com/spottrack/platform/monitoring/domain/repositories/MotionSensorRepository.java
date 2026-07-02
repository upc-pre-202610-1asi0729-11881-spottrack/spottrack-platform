package com.spottrack.platform.monitoring.domain.repositories;

import com.spottrack.platform.monitoring.domain.model.aggregates.MotionSensor;
import com.spottrack.platform.monitoring.domain.model.valueobjects.EquipmentId;
import org.springframework.stereotype.Repository;

@Repository
public interface MotionSensorRepository {
    boolean existsByEquipmentId(EquipmentId equipmentId);
    MotionSensor save(MotionSensor motionSensor);
}
