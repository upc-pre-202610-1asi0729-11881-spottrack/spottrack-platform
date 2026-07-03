package com.spottrack.platform.gym.interfaces.acl;

import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;

import java.util.List;
import java.util.Optional;

public interface GymContextFacade {

    void updateEquipmentStatus(String equipmentId, EquipmentStatus status);

    Optional<Equipment> findEquipmentById(String equipmentId);

    /**
     * Available equipment of the same name/kind, excluding the given equipmentId.
     * Backs the Reservation context's "View Alternatives" read model.
     */
    List<Equipment> findAvailableAlternatives(String equipmentName, String excludeEquipmentId);

    /**
     * Returns the IAM userId of the Admin who owns the given gym.
     * Returns 0L if the gym does not exist.
     */
    Long fetchAdminUserIdByGymId(String gymId);

    boolean isDniWhitelistedForGym(String gymId, String dni);
}
