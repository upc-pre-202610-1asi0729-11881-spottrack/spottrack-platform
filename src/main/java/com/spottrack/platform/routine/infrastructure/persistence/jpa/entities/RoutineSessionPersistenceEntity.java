package com.spottrack.platform.routine.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "routine_sessions")
public class RoutineSessionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "routine_id", nullable = false)
    private Long routineId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "status", nullable = false)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "started_at", nullable = false)
    private Date startedAt;

    public Long getRoutineId() { return routineId; }
    public void setRoutineId(Long routineId) { this.routineId = routineId; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getStartedAt() { return startedAt; }
    public void setStartedAt(Date startedAt) { this.startedAt = startedAt; }
}
