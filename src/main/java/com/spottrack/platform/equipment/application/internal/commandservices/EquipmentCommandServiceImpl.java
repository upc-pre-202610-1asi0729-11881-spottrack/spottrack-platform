package com.spottrack.platform.equipment.application.internal.commandservices;

import com.spottrack.platform.equipment.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.commands.DefineMaintenanceThreshold;
import com.spottrack.platform.equipment.domain.model.commands.MarkEquipmentOutOfService;
import com.spottrack.platform.equipment.domain.model.commands.RegisterEquipment;
import com.spottrack.platform.equipment.domain.model.queries.GetEquipmentById;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities.EquipmentPersistenceEntity;
import com.spottrack.platform.equipment.infrastructure.persistence.jpa.repositories.EquipmentPersistenceRepository;
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
        var entity = EquipmentPersistenceAssembler.toPersistenceFromDomain(equipment);
        var savedEntity = equipmentRepository.save(entity);
        var savedEquipment = EquipmentPersistenceAssembler.toDomainFromPersistence(savedEntity);

        return Result.success(savedEquipment);
    }

    @Override
    public Result<Equipment, ApplicationError> handle(DefineMaintenanceThreshold command){
        return Result.failure(ApplicationError.unexpected("DefineMaintenanceThreshold", "Not implemented yet"));

    }
    @Override
    public Result<Equipment, ApplicationError> handle(MarkEquipmentOutOfService command) {
        var equipment = equipmentRepository.findByEquipmentId(command.id().uuid());
        var domain = EquipmentPersistenceAssembler.toDomainFromPersistence(equipment.get());
        domain.markEquipmentOutOfService();
        return Result.success(domain);
    }
}
