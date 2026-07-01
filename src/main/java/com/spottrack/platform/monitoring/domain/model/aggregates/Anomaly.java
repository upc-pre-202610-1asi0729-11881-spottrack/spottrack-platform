package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.commands.ReportAnomalyCommand;
import com.spottrack.platform.monitoring.domain.model.valueobjects.AnomalyId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ZoneId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Anomaly extends AbstractDomainAggregateRoot<Anomaly> {
    private Long id;
    private AnomalyId anomalyId;
    private EquipmentId equipmentId;
    private ZoneId zoneId;
    private String anomalyDescription;


    public Anomaly(Long id, AnomalyId anomalyId, EquipmentId equipmentId, ZoneId zoneId, String anomalyDescription) {
        this.id = id;
        this.anomalyId = anomalyId;
        this.equipmentId = equipmentId;
        this.zoneId = zoneId;
        this.anomalyDescription = anomalyDescription;
    }

    public Anomaly(ReportAnomalyCommand command){
        this.id = 0L;
        this.anomalyId = new AnomalyId();
        this.equipmentId = new EquipmentId(command.equipmentId());
        this.zoneId = new ZoneId(command.zoneId());
        this.anomalyDescription = command.anomalyDescription();
    }
}
