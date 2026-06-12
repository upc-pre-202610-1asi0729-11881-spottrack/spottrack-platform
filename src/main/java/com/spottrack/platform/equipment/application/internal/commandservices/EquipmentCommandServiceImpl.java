package com.spottrack.platform.equipment.application.internal.commandservices;

import com.spottrack.platform.equipment.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.commands.DefineMaintenanceThreshold;
import com.spottrack.platform.equipment.domain.model.commands.MarkEquipmentOutOfService;
import com.spottrack.platform.equipment.domain.model.commands.RegisterEquipment;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.EquipmentPersistenceRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EquipmentCommandServiceImpl implements EquipmentCommandService {
    private final EquipmentPersistenceRepository equipmentRepository;

    public EquipmentCommandServiceImpl(EquipmentPersistenceRepository equipmentRepository){
        this.equipmentRepository = equipmentRepository;
    }

    @Transactional
    @Override
    public Result<Equipment, ApplicationError> handle(RegisterEquipment command){
        var equipment = new Equipment(command);
        var saved = equipmentRepository.save(equipment);
        return Result.success(saved);
    }

    @Override
    public Result<Equipment, ApplicationError> handle(DefineMaintenanceThreshold command){
        return Result.failure(ApplicationError.unexpected("DefineMaintenanceThreshold", "Not implemented yet"));

    }
    @Override
    public Result<Equipment, ApplicationError> handle(MarkEquipmentOutOfService command) {
        return Result.failure(ApplicationError.unexpected("MarkEquipmentOutOfService", "Not implemented yet"));
    }
}
