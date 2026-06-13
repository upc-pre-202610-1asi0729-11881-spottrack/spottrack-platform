package com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.equipment.domain.model.valueobjects.BranchId;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.equipment.domain.model.valueobjects.ZoneId;
import com.spottrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.OneToMany;

import java.util.List;

public class ZonePersistenceEntity extends AuditableAbstractPersistenceEntity {
    private String id;


    private String name;
    private int maximumOccupancy;

    private String branchId;

    @OneToMany
    private List<EquipmentPersistenceEntity> equipmentList;
}
