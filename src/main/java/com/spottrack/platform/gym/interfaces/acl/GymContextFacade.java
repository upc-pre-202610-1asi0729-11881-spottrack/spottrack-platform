package com.spottrack.platform.gym.interfaces.acl;

import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;

import java.util.Optional;

public interface GymContextFacade {

    void updateEquipmentStatus(String equipmentId, EquipmentStatus status);

    Optional<Equipment> findEquipmentById(String equipmentId);

    /**
     * Returns the IAM userId of the Admin who owns the given gym.
     * Returns 0L if the gym does not exist.
     */
    Long fetchAdminUserIdByGymId(String gymId);
}
