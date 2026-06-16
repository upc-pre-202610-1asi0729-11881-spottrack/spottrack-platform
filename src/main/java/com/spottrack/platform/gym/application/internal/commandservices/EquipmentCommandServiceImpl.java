package com.spottrack.platform.gym.application.internal.commandservices;

import com.spottrack.platform.gym.application.commandServices.EquipmentCommandService;
import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.commands.*;
import com.spottrack.platform.gym.domain.model.queries.GetEquipmentById;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.assemblers.EquipmentPersistenceAssembler;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.entities.EquipmentPersistenceEntity;
import com.spottrack.platform.gym.infrastructure.persistence.jpa.repositories.EquipmentPersistenceRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EquipmentCommandServiceImpl implements EquipmentCommandService {
    private final EquipmentPersistenceRepository equipmentRepository;


    public EquipmentCommandServiceImpl(EquipmentPersistenceRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;


    }

    @Transactional
    @Override
    public Result<Equipment, ApplicationError> handle(RegisterEquipment command) {
        var equipment = new Equipment(command);
        var entity = EquipmentPersistenceAssembler.toPersistenceFromDomain(equipment);
        var savedEntity = equipmentRepository.save(entity);
        var savedEquipment = EquipmentPersistenceAssembler.toDomainFromPersistence(savedEntity);

        return Result.success(savedEquipment);
    }



    @Override
    public Result<Equipment, ApplicationError> handle(MarkEquipmentOutOfService command) {
        var entity = equipmentRepository.findByEquipmentId(command.id().uuid());
        var equipment = EquipmentPersistenceAssembler.toDomainFromPersistence(entity.get());
        equipment.markEquipmentOutOfService();
        var updatedEntity = EquipmentPersistenceAssembler.toPersistenceFromDomain(equipment);
        updatedEntity.setId(entity.get().getId());
        equipmentRepository.save(updatedEntity);
        return Result.success(equipment);
    }

    @Override
    public Result<Equipment, ApplicationError> handle(UpdateEquipmentStatus command) {
        try {
            var entity = equipmentRepository.findByEquipmentId(command.id());
            if (entity.isEmpty()) {
                return Result.failure(ApplicationError.notFound("Equipment", command.id()));
            }
            var equipment = EquipmentPersistenceAssembler.toDomainFromPersistence(entity.get());
            equipment.updateStatus(command.status());
            var updatedEntity = EquipmentPersistenceAssembler.toPersistenceFromDomain(equipment);
            updatedEntity.setId(entity.get().getId());
            equipmentRepository.save(updatedEntity);
            return Result.success(equipment);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Equipment", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Equipment status update", e.getMessage()));
        }
    }

    @Override
    public Result<Equipment, ApplicationError> handle(RelocateEquipment command) {
        var entity = equipmentRepository.findByEquipmentId(command.equipmentId().uuid());

        var domain = EquipmentPersistenceAssembler.toDomainFromPersistence(entity.get());

        domain.relocateEquipment(command.zoneId());
        var updatedEntity = EquipmentPersistenceAssembler.toPersistenceFromDomain(domain);
        equipmentRepository.save(updatedEntity);
        return Result.success(domain);
    }

    @Transactional
    @Override
    public Result<Equipment, ApplicationError> handle(DecomissionEquipment command) {
        var entity = equipmentRepository.findByEquipmentId(command.equipmentId().uuid());
        var persistenceEntity = entity.get();
        persistenceEntity.setStatus(command.equipmentStatus());
        equipmentRepository.save(persistenceEntity);
        var equipment = EquipmentPersistenceAssembler.toDomainFromPersistence(persistenceEntity);
        return Result.success(equipment);
    }

    @Override
    public Result<Equipment, ApplicationError> handle(DefineMaintenanceThresholdCommand command) {
        var entity = equipmentRepository.findByEquipmentId(command.equipmentId().uuid());
        var persistenceEntity = entity.get();
        var domainEntity = EquipmentPersistenceAssembler.toDomainFromPersistence(persistenceEntity);
        domainEntity.setMaintenanceThreshold(command.threshold());
        var updatedEntity = EquipmentPersistenceAssembler.toPersistenceFromDomain(domainEntity);
        updatedEntity.setId(persistenceEntity.getId());
        equipmentRepository.save(updatedEntity);
        return Result.success(domainEntity);
    }

}
