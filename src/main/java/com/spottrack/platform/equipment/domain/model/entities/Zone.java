package com.spottrack.platform.equipment.domain.model.entities;

import com.spottrack.platform.equipment.domain.model.valueobjects.ZoneId;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class Zone {
    private final ZoneId id;
    private String name;
    private int maximumOccupancy;


    protected Zone() {
        this.id = null;
    }

    public Zone(String name, int maximumOccupancy) {
        this.id = new ZoneId(UUID.randomUUID().toString());
        this.name = name;
        this.maximumOccupancy = maximumOccupancy;
    }
}
