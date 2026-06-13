package com.spottrack.platform.gym.application.internal.commandservices;

import com.spottrack.platform.gym.application.commandServices.GymCommandService;
import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.aggregates.Gym;
import com.spottrack.platform.gym.domain.model.commands.CreateGym;
import com.spottrack.platform.gym.domain.model.commands.RelocateEquipment;
import com.spottrack.platform.gym.domain.model.commands.RequestEquipmentRelocation;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.GymPersistenceAssembler;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.entities.GymPersistenceEntity;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.EquipmentPersistenceRepository;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.GymPersistenceRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
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

    @Transactional
    @Override
    public Result<Gym, ApplicationError> handle(CreateGym command) {
        var gym = new Gym(command.gymName());
        var gymEntity = GymPersistenceAssembler.toPersistenceFromDomain(gym);
        var savedEntity = gymPersistenceRepository.save(gymEntity);
        return Result.success(gym);
    }

}
