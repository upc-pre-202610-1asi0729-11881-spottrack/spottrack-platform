package com.spottrack.platform.maintenance.domain.model.aggregates;

import com.spottrack.platform.maintenance.domain.model.commands.RegisterMaintenanceCompletion;
import com.spottrack.platform.maintenance.domain.model.events.MaintenanceCompletionRegisteredToLogEvent;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class MaintenanceLog extends AbstractDomainAggregateRoot<MaintenanceLog> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String logId;

    @Column(nullable = false)
    private String ticketId;

    @Column(nullable = false)
    private String maintenanceId;

    @Column(nullable = false)
    private String notes;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    protected MaintenanceLog() {}

    public MaintenanceLog(RegisterMaintenanceCompletion command) {
        this.logId = UUID.randomUUID().toString();
        this.ticketId = command.ticketId().uuid();
        this.maintenanceId = command.maintenanceId().uuid();
        this.notes = command.notes();
        this.completedAt = LocalDateTime.now();
        registerDomainEvent(new MaintenanceCompletionRegisteredToLogEvent(this.logId, this.ticketId, this.maintenanceId));
    }
}
