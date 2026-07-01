package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.monitoring.domain.model.valueobjects.CameraSensorId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ZoneId;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.converters.CameraSensorIdPersistenceConverter;
import com.spottrack.platform.monitoring.infrastructure.persistence.jpa.converters.ZoneIdPersistenceConverter;
import com.spottrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "camera_sensors")
@Getter
@Setter
public class CameraSensorPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Embedded
    @Convert(converter = CameraSensorIdPersistenceConverter.class)
    @Column(nullable = false, unique = true)
    private CameraSensorId cameraSensorId;

    @Embedded
    @Convert(converter = ZoneIdPersistenceConverter.class)
    @Column(nullable = false, unique = true)
    private ZoneId zoneId;

    @Column(nullable = false)
    private LocalDateTime registeredAt;
}
