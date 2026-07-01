package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.assemblers;


import com.spottrack.platform.monitoring.domain.model.aggregates.Anomaly;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities.AnomalyPersistenceEntity;

public class AnomalyPersistenceAssembler {
    public static Anomaly toDomainFromPersistence(AnomalyPersistenceEntity entity){
        return new Anomaly(entity.getId(), entity.getAnomalyId(),entity.getEquipmentId(), entity.getZoneId(), entity.getAnomalyDescription());
    }

    public static AnomalyPersistenceEntity toPersistenceFromDomain(Anomaly entity){
        var persistence = new AnomalyPersistenceEntity();
        persistence.setId(entity.getId());
        persistence.setAnomalyId(entity.getAnomalyId());
        persistence.setEquipmentId(entity.getEquipmentId());
        persistence.setZoneId(entity.getZoneId());
        persistence.setAnomalyDescription(entity.getAnomalyDescription());
        return persistence;
    }

}
