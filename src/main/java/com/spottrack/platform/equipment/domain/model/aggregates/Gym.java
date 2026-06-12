package com.spottrack.platform.equipment.domain.model.aggregates;

import com.spottrack.platform.equipment.domain.model.entities.Branch;
import com.spottrack.platform.equipment.domain.model.valueobjects.GymId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Gym extends AbstractDomainAggregateRoot<Gym> {

    @OneToMany
    private List<Branch> branchList;

    @EmbeddedId
    private GymId id;
    private String name;


    protected Gym() {}

    public Gym(String name) {
        this.id = new GymId(UUID.randomUUID().toString());
        this.name = name;
        this.branchList = new ArrayList<Branch>();
    }
}
