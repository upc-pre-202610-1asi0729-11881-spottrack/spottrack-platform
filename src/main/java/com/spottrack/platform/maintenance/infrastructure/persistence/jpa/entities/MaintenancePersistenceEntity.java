package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.entities;


import com.spottrack.platform.maintenance.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceStatus;
import com.spottrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="maintenances")
@Getter
@Setter
@NoArgsConstructor
public class MaintenancePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String maintenanceId;


    @Column(nullable = false, unique = false)
    private String equipmentId;

    @Column(nullable = false)
    private String requestedBy;

    /**
     * A description of the reason of the maintenance is required
     */
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private MaintenanceStatus status;
}
