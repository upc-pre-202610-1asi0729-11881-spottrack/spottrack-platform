package com.spottrack.platform.equipment.domain.model.entities;

import com.spottrack.platform.equipment.domain.model.valueobjects.BranchId;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.ZoneId;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Zone {
    private final ZoneId id;


    private String name;
    private int maximumOccupancy;

    private BranchId branch;

    private List<EquipmentId> equipmentList;

    protected Zone() {
        this.id = null;
    }

    public Zone(String name, int maximumOccupancy) {
        this.id = new ZoneId(UUID.randomUUID().toString());
        this.name = name;
        this.maximumOccupancy = maximumOccupancy;
        this.equipmentList = new ArrayList<EquipmentId>();
    }
}
