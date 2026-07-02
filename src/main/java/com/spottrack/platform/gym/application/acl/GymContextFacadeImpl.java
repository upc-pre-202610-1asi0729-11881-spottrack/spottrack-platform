package com.spottrack.platform.gym.application.acl;

import com.spottrack.platform.gym.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.gym.application.queryservices.EquipmentQueryService;
import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.commands.UpdateEquipmentStatus;
import com.spottrack.platform.gym.domain.model.queries.GetEquipmentById;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.gym.interfaces.acl.GymContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GymContextFacadeImpl implements GymContextFacade {

    private final EquipmentCommandService equipmentCommandService;
    private final EquipmentQueryService equipmentQueryService;

    public GymContextFacadeImpl(EquipmentCommandService equipmentCommandService, EquipmentQueryService equipmentQueryService) {
        this.equipmentCommandService = equipmentCommandService;
        this.equipmentQueryService = equipmentQueryService;
    }

    @Override
    public void updateEquipmentStatus(String equipmentId, EquipmentStatus status) {
        equipmentCommandService.handle(new UpdateEquipmentStatus(equipmentId, status));
    }

    @Override
    public Optional<Equipment> findEquipmentById(String equipmentId) {
        return equipmentQueryService.handle(new GetEquipmentById(new EquipmentId(equipmentId)));
    }
}
