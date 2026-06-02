package com.spottrack.platform.equipment.application.commandServices;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.commands.RegisterEquipment;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

public interface EquipmentCommandService {
    Result<Equipment, ApplicationError> handle(RegisterEquipment command);
}
