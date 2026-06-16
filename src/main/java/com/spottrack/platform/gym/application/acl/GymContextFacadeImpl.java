package com.spottrack.platform.gym.application.acl;

import com.spottrack.platform.gym.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.gym.domain.model.commands.UpdateEquipmentStatus;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.gym.interfaces.acl.GymContextFacade;
import org.springframework.stereotype.Service;

@Service
public class GymContextFacadeImpl implements GymContextFacade {

    private final EquipmentCommandService equipmentCommandService;

    public GymContextFacadeImpl(EquipmentCommandService equipmentCommandService) {
        this.equipmentCommandService = equipmentCommandService;
    }

    @Override
    public void updateEquipmentStatus(String equipmentId, EquipmentStatus status) {
        equipmentCommandService.handle(new UpdateEquipmentStatus(equipmentId, status));
    }
}
