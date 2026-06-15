package com.spottrack.platform.maintenance.infrastructure.persistence.jpa.entites;

import com.spottrack.platform.maintenance.domain.model.commands.RequestMaintenance;
import com.spottrack.platform.maintenance.domain.model.events.MaintenanceRequestedEvent;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Table(name="maintenances")
@NoArgsConstructor
@Getter
@Setter
public class MaintenancePersistenceEntity {
    @EmbeddedId
    private MaintenanceId id;

    @Column(nullable = false)
    private String equipmentId;

    @Column(nullable = false)
    private String requestedBy;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceStatus status;

}

