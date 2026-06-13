package com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="gyms")
public class GymPersistenceEntity {
    String gymId;
    String name;

}



