package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.entities.Report;
import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyType;
import com.spottrack.platform.monitoring.domain.model.valueobjects.DamageType;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ReportId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import org.springframework.data.domain.AbstractAggregateRoot;

public class Anomaly extends AbstractDomainAggregateRoot {
    AnomalyId anomalyId;
    Report report;
    DamageType damageType;
    AnomalyType anomalyType;
}
