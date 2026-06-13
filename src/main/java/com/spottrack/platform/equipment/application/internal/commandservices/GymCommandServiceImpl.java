package com.spottrack.platform.equipment.application.internal.commandservices;

import com.spottrack.platform.equipment.application.commandServices.GymCommandService;
import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.commands.RelocateEquipment;
import com.spottrack.platform.equipment.domain.model.commands.RequestEquipmentRelocation;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.repositories.EquipmentPersistenceRepository;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.repositories.GymPersistenceRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

public class GymCommandServiceImpl implements GymCommandService {
    GymPersistenceRepository gymPersistenceRepository;
    EquipmentPersistenceRepository equipmentPersistenceRepository;

    public GymCommandServiceImpl(GymPersistenceRepository gymPersistenceRepository, EquipmentPersistenceRepository equipmentPersistenceRepository){
        this.gymPersistenceRepository = gymPersistenceRepository;
        this.equipmentPersistenceRepository = equipmentPersistenceRepository;
    }



    @Override
    public Result<Equipment, ApplicationError> handle(RequestEquipmentRelocation command) {
        return null;
    }

}
