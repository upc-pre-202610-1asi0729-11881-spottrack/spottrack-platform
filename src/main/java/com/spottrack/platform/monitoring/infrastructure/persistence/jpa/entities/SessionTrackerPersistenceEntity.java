package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="session_trackers")
@NoArgsConstructor
@Getter
@Setter
public class SessionTrackerPersistenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * For security measures, we will use uuids as secondary Ids aside from the real DB Long Ids
     */
    @Column(nullable = false, unique = true)
    String sessionTrackerId;
    /**
     * id of the reservation that the session tracker is monitoring
     */
    @Column(nullable = false, unique = true)
    String  reservationId;
    @Column(nullable = false)
    boolean sessionIsInactive;
    @Column(nullable = false)
    boolean sessionIsActive;
    @Column(nullable = false)
    LocalTime seconds;
    @Column(nullable = false)
    LocalTime continuousActivity;
    @Column
    LocalDateTime lastActivityAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "session_tracker_id")
    private List<AnomalyReportPersistenceEntity> anomalyReports = new ArrayList<>();

}
