package com.spottrack.platform.gym.application.internal.queryservices;

import com.spottrack.platform.gym.application.queryservices.GymQueryService;
import com.spottrack.platform.gym.domain.model.aggregates.Gym;
import com.spottrack.platform.gym.domain.model.queries.GetGymById;
import com.spottrack.platform.gym.domain.model.queries.GetGymsByAdminUserId;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.GymPersistenceAssembler;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.GymPersistenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GymQueryServiceImpl implements GymQueryService {
    GymPersistenceRepository gymPersistenceRepository;

    public GymQueryServiceImpl(GymPersistenceRepository gymPersistenceRepository) {
        this.gymPersistenceRepository = gymPersistenceRepository;
    }

    @Override
    public Optional<Gym> handle(GetGymById query) {
        return gymPersistenceRepository.findByGymId(query.id().uuid())
                .map(GymPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Gym> handle(GetGymsByAdminUserId query) {
        return gymPersistenceRepository.findByAdminUserId(query.adminUserId()).stream()
                .map(GymPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
