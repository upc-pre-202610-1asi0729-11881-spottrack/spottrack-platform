package com.spottrack.platform.maintenance.domain.model.aggregates;

import com.spottrack.platform.maintenance.domain.model.commands.AssignTechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.commands.CreateTechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.commands.ModifyTicketStatus;
import com.spottrack.platform.maintenance.domain.model.commands.RequestUpdateMaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.commands.UpdateMaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.events.MaintenanceStatusUpdateRequestedEvent;
import com.spottrack.platform.maintenance.domain.model.events.MaintenanceStatusUpdatedEvent;
import com.spottrack.platform.maintenance.domain.model.events.TechnicalTicketAssignedEvent;
import com.spottrack.platform.maintenance.domain.model.events.TechnicalTicketCreatedEvent;
import com.spottrack.platform.maintenance.domain.model.events.TicketStatusMarkedAsResolvedEvent;
import com.spottrack.platform.maintenance.domain.model.events.TicketStatusModifiedEvent;
import com.spottrack.platform.maintenance.domain.model.valueobjects.MaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TechnicalTicketId;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketStatus;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class TechnicalTicket extends AbstractDomainAggregateRoot<TechnicalTicket> {

    @EmbeddedId
    private TechnicalTicketId id;

    @Column(nullable = false)
    private String maintenanceId;

    @Column(nullable = false)
    private String equipmentId;

    @Column(nullable = true)
    private String technicianId;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus ticketStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceStatus maintenanceStatus;

    protected TechnicalTicket() {}

    public TechnicalTicket(CreateTechnicalTicket command) {
        this.id = new TechnicalTicketId(UUID.randomUUID().toString());
        this.maintenanceId = command.maintenanceId().uuid();
        this.equipmentId = command.equipmentId();
        this.description = command.description();
        this.ticketStatus = TicketStatus.OPEN;
        this.maintenanceStatus = MaintenanceStatus.REQUESTED;
        registerDomainEvent(new TechnicalTicketCreatedEvent(this.id.uuid(), this.maintenanceId, this.equipmentId));
    }

    public void assign(AssignTechnicalTicket command) {
        this.technicianId = command.technicianId();
        this.ticketStatus = TicketStatus.IN_PROGRESS;
        registerDomainEvent(new TechnicalTicketAssignedEvent(this.id.uuid(), this.technicianId));
    }

    public void markAsResolved() {
        if (this.ticketStatus == TicketStatus.RESOLVED) {
            throw new IllegalStateException("maintenance.error.ticket.alreadyResolved");
        }
        this.ticketStatus = TicketStatus.RESOLVED;
        this.maintenanceStatus = MaintenanceStatus.COMPLETED;
        registerDomainEvent(new TicketStatusMarkedAsResolvedEvent(this.id.uuid(), this.equipmentId));
    }

    public void modifyStatus(ModifyTicketStatus command) {
        this.ticketStatus = command.newStatus();
        registerDomainEvent(new TicketStatusModifiedEvent(this.id.uuid(), command.newStatus().name()));
    }

    public void requestStatusUpdate(RequestUpdateMaintenanceStatus command) {
        registerDomainEvent(new MaintenanceStatusUpdateRequestedEvent(this.id.uuid(), command.newStatus().name()));
    }

    public void updateMaintenanceStatus(UpdateMaintenanceStatus command) {
        this.maintenanceStatus = command.newStatus();
        registerDomainEvent(new MaintenanceStatusUpdatedEvent(this.id.uuid(), command.newStatus().name()));
    }
}
