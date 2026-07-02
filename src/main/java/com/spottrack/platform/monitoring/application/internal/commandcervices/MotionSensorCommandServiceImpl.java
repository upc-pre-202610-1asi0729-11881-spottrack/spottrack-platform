package com.spottrack.platform.monitoring.application.internal.commandcervices;

import com.spottrack.platform.monitoring.application.commandServices.MotionSensorCommandService;
import com.spottrack.platform.monitoring.domain.model.aggregates.MotionSensor;
import com.spottrack.platform.monitoring.domain.model.commands.RegisterMotionSensorCommand;
import com.spottrack.platform.monitoring.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.monitoring.domain.repositories.MotionSensorRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class MotionSensorCommandServiceImpl implements MotionSensorCommandService {
    private final MotionSensorRepository motionSensorRepository;

    public MotionSensorCommandServiceImpl(MotionSensorRepository motionSensorRepository) {
        this.motionSensorRepository = motionSensorRepository;
    }

    @Override
    public Result<MotionSensor, ApplicationError> handle(RegisterMotionSensorCommand command) {
        try {
            if (motionSensorRepository.existsByEquipmentId(new EquipmentId(command.equipmentId()))) {
                return Result.failure(ApplicationError.conflict(
                        "MotionSensor",
                        "A motion sensor is already registered for equipment '%s'".formatted(command.equipmentId())
                ));
            }
            var motionSensor = new MotionSensor(command);
            var saved = motionSensorRepository.save(motionSensor);
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("MotionSensor", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Motion sensor registration", e.getMessage()));
        }
    }
}
