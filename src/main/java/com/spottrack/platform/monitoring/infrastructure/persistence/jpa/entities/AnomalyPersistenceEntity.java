package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.monitoring.domain.model.entities.Report;
import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyType;
import com.spottrack.platform.monitoring.domain.model.valueobjects.DamageType;
import com.spottrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="anomalies")
@Getter
@Setter
@NoArgsConstructor
public class AnomalyPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false)
    Long anomalyId;

    DamageType damageType;
    AnomalyType anomalyType;
}
