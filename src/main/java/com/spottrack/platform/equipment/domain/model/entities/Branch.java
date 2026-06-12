package com.spottrack.platform.equipment.domain.model.entities;

import com.spottrack.platform.equipment.domain.model.valueobjects.BranchId;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
public class Branch {

    @EmbeddedId
    private final BranchId id;
    private String name;
    private String address;

    @OneToMany
    private List<Zone> zoneList;
    protected Branch() {
        this.id = null;
    }

    public Branch(String name, String address) {
        this.id = new BranchId(UUID.randomUUID().toString());
        this.name = name;
        this.address = address;
        this.zoneList = new ArrayList<Zone>();
    }
}
