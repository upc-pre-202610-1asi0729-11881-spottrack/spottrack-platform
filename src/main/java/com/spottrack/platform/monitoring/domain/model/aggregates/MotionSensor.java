package com.spottrack.platform.monitoring.domain.model.aggregates;

import com.spottrack.platform.monitoring.domain.model.commands.RegisterMotionSensorCommand;
import com.spottrack.platform.monitoring.domain.model.events.MotionSensorRegisteredEvent;
import com.spottrack.platform.monitoring.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.MotionSensorId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MotionSensor extends AbstractDomainAggregateRoot<MotionSensor> {
    private Long id;
    private MotionSensorId motionSensorId;
    private EquipmentId equipmentId;
    private LocalDateTime registeredAt;

    public MotionSensor(Long id, MotionSensorId motionSensorId, EquipmentId equipmentId, LocalDateTime registeredAt) {
        this.id = id;
        this.motionSensorId = motionSensorId;
        this.equipmentId = equipmentId;
        this.registeredAt = registeredAt;
    }

    public MotionSensor(RegisterMotionSensorCommand command) {
        this.id = null;
        this.motionSensorId = new MotionSensorId();
        this.equipmentId = new EquipmentId(command.equipmentId());
        this.registeredAt = LocalDateTime.now();
    }

    public void onCreated() {
        registerDomainEvent(new MotionSensorRegisteredEvent(this.motionSensorId, this.equipmentId));
    }
}
