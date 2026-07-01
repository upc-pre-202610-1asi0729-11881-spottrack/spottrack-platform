package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyType;
import com.spottrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "anomaly_reports")
@Getter
@Setter
@NoArgsConstructor
public class AnomalyReportPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String anomalyReportId;

    @Column(nullable = false)
    private String sessionTrackerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnomalyType anomalyType;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime reportedAt;

    @Column(nullable = false)
    private boolean resolved;
}
