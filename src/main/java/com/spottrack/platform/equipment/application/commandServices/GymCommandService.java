package com.spottrack.platform.equipment.application.commandServices;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.commands.RelocateEquipment;
import com.spottrack.platform.equipment.domain.model.commands.RequestEquipmentRelocation;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

public interface GymCommandService {
    public Result<Equipment, ApplicationError> handle(RequestEquipmentRelocation command);
}
