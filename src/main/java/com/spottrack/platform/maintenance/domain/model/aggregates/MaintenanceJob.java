package com.spottrack.platform.maintenance.domain.model.aggregates;

import com.spottrack.platform.maintenance.domain.model.commands.AcceptMaintenance;
import com.spottrack.platform.maintenance.domain.model.events.MaintenanceJobAcceptedEvent;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceJobId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class MaintenanceJob extends AbstractDomainAggregateRoot<MaintenanceJob> {

    @EmbeddedId
    private MaintenanceJobId id;

    @Column(nullable = true)
    private String technicianId;

    @Column(nullable = false)
    private String maintenanceId;

    @Column(nullable = false)
    private boolean accepted;

    protected MaintenanceJob() {}

    public MaintenanceJob(String maintenanceId) {
        this.id = new MaintenanceJobId(UUID.randomUUID().toString());
        this.maintenanceId = maintenanceId;
        this.accepted = false;
    }

    public void accept(AcceptMaintenance command) {
        if (this.accepted) {
            throw new IllegalStateException("maintenance.error.job.alreadyAccepted");
        }
        this.technicianId = command.technicianId();
        this.accepted = true;
        registerDomainEvent(new MaintenanceJobAcceptedEvent(this.id.uuid(), this.technicianId));
    }
}
