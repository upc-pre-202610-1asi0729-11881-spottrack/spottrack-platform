package com.spottrack.platform.gym.interfaces.acl;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;

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
}
