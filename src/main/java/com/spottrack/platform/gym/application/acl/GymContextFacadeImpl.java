package com.spottrack.platform.gym.application.acl;

import com.spottrack.platform.gym.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.gym.application.queryservices.EquipmentQueryService;
import com.spottrack.platform.gym.application.queryservices.GymQueryService;
import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.commands.UpdateEquipmentStatus;
import com.spottrack.platform.gym.domain.model.queries.GetAvailableAlternativesQuery;
import com.spottrack.platform.gym.domain.model.queries.GetEquipmentById;
import com.spottrack.platform.gym.domain.model.queries.GetGymById;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.gym.domain.model.valueobjects.GymId;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.GymWhitelistPersistenceRepository;
import com.spottrack.platform.gym.interfaces.acl.GymContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GymContextFacadeImpl implements GymContextFacade {

    private final EquipmentCommandService equipmentCommandService;
    private final EquipmentQueryService equipmentQueryService;
    private final GymQueryService gymQueryService;
    private final GymWhitelistPersistenceRepository gymWhitelistPersistenceRepository;

    public GymContextFacadeImpl(EquipmentCommandService equipmentCommandService,
                                EquipmentQueryService equipmentQueryService,
                                GymQueryService gymQueryService,
                                GymWhitelistPersistenceRepository gymWhitelistPersistenceRepository) {
        this.equipmentCommandService = equipmentCommandService;
        this.equipmentQueryService = equipmentQueryService;
        this.gymQueryService = gymQueryService;
        this.gymWhitelistPersistenceRepository = gymWhitelistPersistenceRepository;
    }

    @Override
    public void updateEquipmentStatus(String equipmentId, EquipmentStatus status) {
        equipmentCommandService.handle(new UpdateEquipmentStatus(equipmentId, status));
    }

    @Override
    public Optional<Equipment> findEquipmentById(String equipmentId) {
        return equipmentQueryService.handle(new GetEquipmentById(new EquipmentId(equipmentId)));
    }

    @Override
    public List<Equipment> findAvailableAlternatives(String equipmentName, String excludeEquipmentId) {
        return equipmentQueryService.handle(new GetAvailableAlternativesQuery(equipmentName, excludeEquipmentId));
    }

    @Override
    public Long fetchAdminUserIdByGymId(String gymId) {
        return gymQueryService.handle(new GetGymById(new GymId(gymId)))
                .map(gym -> gym.getAdminUserId() != null ? gym.getAdminUserId() : 0L)
                .orElse(0L);
    }

    @Override
    public boolean isDniWhitelistedForGym(String gymId, String dni) {
        return gymWhitelistPersistenceRepository.existsByGymIdAndDni(gymId, dni);
    }
}
