package com.spottrack.platform.maintenance.domain.model.aggregates;

import com.spottrack.platform.maintenance.domain.model.commands.RegisterMaintenanceCompletion;
import com.spottrack.platform.maintenance.domain.model.events.MaintenanceCompletionRegisteredToLogEvent;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceLogId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class MaintenanceLog extends AbstractDomainAggregateRoot<MaintenanceLog> {

    @EmbeddedId
    private MaintenanceLogId id;

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
        this.id = new MaintenanceLogId(UUID.randomUUID().toString());
        this.ticketId = command.ticketId().uuid();
        this.maintenanceId = command.maintenanceId().uuid();
        this.notes = command.notes();
        this.completedAt = LocalDateTime.now();
        registerDomainEvent(new MaintenanceCompletionRegisteredToLogEvent(this.id.uuid(), this.ticketId, this.maintenanceId));
    }
}
