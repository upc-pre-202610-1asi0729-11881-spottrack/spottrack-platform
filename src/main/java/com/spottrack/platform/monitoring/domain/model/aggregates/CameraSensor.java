package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.commands.RegisterCameraSensorCommand;
import com.spottrack.platform.monitoring.domain.model.events.CameraSensorRegisteredEvent;
import com.spottrack.platform.monitoring.domain.model.valueobjects.CameraSensorId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ZoneId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CameraSensor extends AbstractDomainAggregateRoot<CameraSensor> {
    private Long id;
    private CameraSensorId cameraSensorId;
    private ZoneId zoneId;
    private LocalDateTime registeredAt;

    public CameraSensor(Long id, CameraSensorId cameraSensorId, ZoneId zoneId, LocalDateTime registeredAt) {
        this.id = id;
        this.cameraSensorId = cameraSensorId;
        this.zoneId = zoneId;
        this.registeredAt = registeredAt;
    }

    public CameraSensor(RegisterCameraSensorCommand command) {
        this.id = null;
        this.cameraSensorId = new CameraSensorId();
        this.zoneId = new ZoneId(command.zoneId());
        this.registeredAt = LocalDateTime.now();
    }

    public void onCreated() {
        registerDomainEvent(new CameraSensorRegisteredEvent(this.cameraSensorId, this.zoneId));
    }
}
