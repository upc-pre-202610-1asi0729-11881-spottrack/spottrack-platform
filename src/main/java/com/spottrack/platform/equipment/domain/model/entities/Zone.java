package com.spottrack.platform.equipment.domain.model.entities;

import com.spottrack.platform.equipment.domain.model.valueobjects.ZoneId;

public class Zone {
    ZoneId id;
    String name;
    /**
     * Capacity limit here
     * |
     * v
     */
    int maximumOccupancy;

}
