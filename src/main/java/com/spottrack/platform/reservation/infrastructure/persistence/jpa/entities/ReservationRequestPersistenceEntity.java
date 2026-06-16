package com.spottrack.platform.reservation.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestId;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="reservation_requests")
@NoArgsConstructor
@Getter
@Setter
public class ReservationRequestPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String equipmentId;

    @Enumerated
    @Column(nullable = true)
    private ReservationRequestStatus status;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

}
