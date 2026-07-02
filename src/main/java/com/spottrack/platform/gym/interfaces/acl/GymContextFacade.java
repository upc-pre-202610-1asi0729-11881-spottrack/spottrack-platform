package com.spottrack.platform.gym.interfaces.acl;

import com.spottrack.platform.gym.domain.model.aggregates.Equipment;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;

import java.util.Optional;

/**
 * ACL facade exposing Gym bounded context capabilities to other contexts.
 */


public interface GymContextFacade {

    /**
     * Update equipment status as an integration event can handle the status changes
     * @param equipmentId
     * @param status
     */
    void updateEquipmentStatus(String equipmentId, EquipmentStatus status);

    /**
     * Fetches an equipment by its UUID for other contexts to read (e.g. a sensor
     * attached to this equipment).
     * @param equipmentId the equipment's UUID
     */
    Optional<Equipment> findEquipmentById(String equipmentId);
}
