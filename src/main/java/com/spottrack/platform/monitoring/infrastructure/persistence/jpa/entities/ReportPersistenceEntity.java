package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities;


import com.spottrack.platform.monitoring.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ReportId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ZoneId;
import com.spottrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name="reports")
@NoArgsConstructor
@Getter
@Setter
public class ReportPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, unique = true)
    Long reportId;
    @Column(nullable = false)
    LocalTime detectionStamp;
    @Column(nullable = false)
    String targetEquipmentId;
    @Column(nullable = false)
    String zoneId;
    @Column(nullable = false)
    String description;
}
