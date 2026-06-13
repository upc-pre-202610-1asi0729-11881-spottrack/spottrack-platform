package com.spottrack.platform.gym.application.queryservices;

import com.spottrack.platform.gym.domain.model.aggregates.Gym;
import com.spottrack.platform.gym.domain.model.queries.GetGymById;
import com.spottrack.platform.shared.application.result.ApplicationError;

import java.util.Optional;

public interface GymQueryService {
    Optional<Gym> handle(GetGymById query);
}
