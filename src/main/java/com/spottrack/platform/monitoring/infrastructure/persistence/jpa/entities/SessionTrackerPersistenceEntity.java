package com.spottrack.platform.monitoring.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.monitoring.domain.model.valueObjects.ReservationId;
import com.spottrack.platform.monitoring.domain.model.valueObjects.UsageActivity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Entity
@Table(name="session_trackers")
@NoArgsConstructor
@Getter
@Setter
public class SessionTrackerPersistenceEntity {
    /**
     * For security measures, we will use uuids as secondary Ids aside from the real DB Long Ids
     */

    @Column(nullable = false, unique = true)
    String sessionTrackerid;
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
    Time seconds;
    @Column(nullable = false)
    Time continuousActivity;

}
