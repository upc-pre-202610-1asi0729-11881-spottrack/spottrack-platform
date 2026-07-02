package com.spottrack.platform.maintenance.domain.model.commands;

import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketPriority;
import com.spottrack.platform.maintenance.domain.model.valueobjects.TicketType;

public record CreateTechnicalTicketCommand(
        String equipmentId,
        String description,
        TicketPriority priority,
        TicketType type
) {
    public CreateTechnicalTicketCommand {
        if (equipmentId == null || equipmentId.isBlank())
            throw new IllegalArgumentException("maintenance.command.createTechnicalTicket.equipmentId.notBlank");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("maintenance.command.createTechnicalTicket.description.notBlank");
        if (priority == null)
            throw new IllegalArgumentException("maintenance.command.createTechnicalTicket.priority.notNull");
        if (type == null)
            throw new IllegalArgumentException("maintenance.command.createTechnicalTicket.type.notNull");
    }
}
