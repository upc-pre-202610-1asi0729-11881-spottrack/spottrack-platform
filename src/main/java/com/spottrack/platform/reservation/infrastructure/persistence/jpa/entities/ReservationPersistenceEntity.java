package com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationStatus;
import com.spottrack.platform.reservation.domain.model.valueobjects.TimeInterval;
import com.spottrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class ReservationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @EmbeddedId
    private ReservationId id;

    @Column(nullable = false)
    private String clientId;

    // String reference to Equipment bounded context
    @Column(nullable = false)
    private String equipmentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    // Set when StartReservationTimer is handled — null until the timer begins
    @Column(nullable = true)
    private LocalDateTime timerExpiry;

    @Embedded
    private TimeInterval timeInterval;
}
