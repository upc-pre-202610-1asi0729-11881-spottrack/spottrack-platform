package com.spottrack.platform.equipment.domain.repositories;
import com.spottrack.platform.equipment.domain.model.aggregates.Gym;
import com.spottrack.platform.equipment.domain.model.valueobjects.GymId;

public interface GymRepository {
    Gym save(Gym gym);
    Optional<Gym> findById(GymId gymId);

}
