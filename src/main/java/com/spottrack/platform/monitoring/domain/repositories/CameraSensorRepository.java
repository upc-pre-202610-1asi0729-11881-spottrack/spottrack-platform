package com.spottrack.platform.monitoring.domain.repositories;

import com.spottrack.platform.monitoring.domain.model.aggregates.CameraSensor;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ZoneId;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraSensorRepository {
    boolean existsByZoneId(ZoneId zoneId);
    CameraSensor save(CameraSensor cameraSensor);
}
