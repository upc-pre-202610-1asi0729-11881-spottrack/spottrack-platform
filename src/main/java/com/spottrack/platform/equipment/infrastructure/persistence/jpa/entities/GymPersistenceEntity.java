package com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.equipment.domain.model.entities.Branch;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="gyms")
public class GymPersistenceEntity {
    @Column(nullable = false, unique = true)
    private String gymId;

    @Column(nullable = false)
    private String name;


    @OneToMany
    private List<Branch> branchList;

}



