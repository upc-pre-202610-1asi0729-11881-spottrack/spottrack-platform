package com.spottrack.platform.equipment.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.equipment.domain.model.valueobjects.BranchId;
import com.spottrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;

public class BranchPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, unique = true)
    private String branchId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

}
