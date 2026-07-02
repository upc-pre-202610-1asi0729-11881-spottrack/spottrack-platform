package com.spottrack.platform.monitoring.domain.repositories;

import com.spottrack.platform.monitoring.domain.model.aggregates.CameraSensor;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ZoneId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraSensorRepository {
    boolean existsByZoneId(ZoneId zoneId);
    List<CameraSensor> findAll();
    CameraSensor save(CameraSensor cameraSensor);
}
