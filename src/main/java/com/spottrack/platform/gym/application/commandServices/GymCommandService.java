package com.spottrack.platform.gym.application.commandServices;

import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.aggregates.Gym;
import com.spottrack.platform.gym.domain.model.commands.AddBranchCommand;
import com.spottrack.platform.gym.domain.model.commands.CreateGym;
import com.spottrack.platform.gym.domain.model.commands.RequestEquipmentRelocation;
import com.spottrack.platform.gym.domain.model.entities.Branch;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

public interface GymCommandService {
    public Result<Equipment, ApplicationError> handle(RequestEquipmentRelocation command);
    public Result<Gym, ApplicationError> handle(CreateGym command);
    public Result<Branch, ApplicationError> handle(AddBranchCommand command);
}
