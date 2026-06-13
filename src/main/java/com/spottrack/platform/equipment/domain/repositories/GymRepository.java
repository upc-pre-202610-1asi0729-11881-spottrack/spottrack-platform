package com.spottrack.platform.equipment.domain.repositories;

import com.spottrack.platform.equipment.domain.model.aggregates.Gym;

public interface GymRepository {
    Gym save(Gym gym);
}
