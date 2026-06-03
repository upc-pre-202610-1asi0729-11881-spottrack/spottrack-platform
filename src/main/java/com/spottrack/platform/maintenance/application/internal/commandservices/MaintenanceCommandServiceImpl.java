package com.spottrack.platform.maintenance.application.internal.commandservices;

import com.spottrack.platform.maintenance.application.commandServices.MaintenanceCommandService;
import com.spottrack.platform.maintenance.domain.model.aggregates.Maintenance;
import com.spottrack.platform.maintenance.domain.model.aggregates.MaintenanceJob;
import com.spottrack.platform.maintenance.domain.model.aggregates.MaintenanceLog;
import com.spottrack.platform.maintenance.domain.model.aggregates.TechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.commands.AcceptMaintenance;
import com.spottrack.platform.maintenance.domain.model.commands.AssignTechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.commands.CompleteMaintenance;
import com.spottrack.platform.maintenance.domain.model.commands.CreateTechnicalTicket;
import com.spottrack.platform.maintenance.domain.model.commands.DecommissionEquipment;
import com.spottrack.platform.maintenance.domain.model.commands.ModifyTicketStatus;
import com.spottrack.platform.maintenance.domain.model.commands.RecommendEquipmentTransfer;
import com.spottrack.platform.maintenance.domain.model.commands.RegisterMaintenanceCompletion;
import com.spottrack.platform.maintenance.domain.model.commands.RequestMaintenance;
import com.spottrack.platform.maintenance.domain.model.commands.RequestUpdateMaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.commands.UpdateMaintenanceStatus;
import com.spottrack.platform.maintenance.domain.model.events.EquipmentDecommissionedEvent;
import com.spottrack.platform.maintenance.domain.model.events.EquipmentTransferRecommendedEvent;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.MaintenanceJobRepository;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.MaintenanceLogRepository;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.MaintenanceRepository;
import com.spottrack.platform.maintenance.infrastructure.persistence.jpa.TechnicalTicketRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaintenanceCommandServiceImpl implements MaintenanceCommandService {

    private final MaintenanceRepository maintenanceRepository;
    private final TechnicalTicketRepository technicalTicketRepository;
    private final MaintenanceJobRepository maintenanceJobRepository;
    private final MaintenanceLogRepository maintenanceLogRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MaintenanceCommandServiceImpl(
            MaintenanceRepository maintenanceRepository,
            TechnicalTicketRepository technicalTicketRepository,
            MaintenanceJobRepository maintenanceJobRepository,
            MaintenanceLogRepository maintenanceLogRepository,
            ApplicationEventPublisher eventPublisher) {
        this.maintenanceRepository = maintenanceRepository;
        this.technicalTicketRepository = technicalTicketRepository;
        this.maintenanceJobRepository = maintenanceJobRepository;
        this.maintenanceLogRepository = maintenanceLogRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public Result<Maintenance, ApplicationError> handle(RequestMaintenance command) {
        var maintenance = new Maintenance(command);
        var saved = maintenanceRepository.save(maintenance);
        return Result.success(saved);
    }

    @Transactional
    @Override
    public Result<TechnicalTicket, ApplicationError> handle(CreateTechnicalTicket command) {
        var ticket = new TechnicalTicket(command);
        var saved = technicalTicketRepository.save(ticket);
        return Result.success(saved);
    }

    @Transactional
    @Override
    public Result<TechnicalTicket, ApplicationError> handle(AssignTechnicalTicket command) {
        var found = technicalTicketRepository.findById(command.ticketId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("TechnicalTicket", command.ticketId().uuid()));
        }
        var ticket = found.get();
        ticket.assign(command);
        var saved = technicalTicketRepository.save(ticket);
        return Result.success(saved);
    }

    @Transactional
    @Override
    public Result<MaintenanceJob, ApplicationError> handle(AcceptMaintenance command) {
        var found = maintenanceJobRepository.findById(command.maintenanceJobId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("MaintenanceJob", command.maintenanceJobId().uuid()));
        }
        var job = found.get();
        job.accept(command);
        var saved = maintenanceJobRepository.save(job);
        return Result.success(saved);
    }

    @Transactional
    @Override
    public Result<TechnicalTicket, ApplicationError> handle(CompleteMaintenance command) {
        var found = technicalTicketRepository.findById(command.ticketId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("TechnicalTicket", command.ticketId().uuid()));
        }
        var ticket = found.get();
        ticket.markAsResolved();
        var saved = technicalTicketRepository.save(ticket);
        return Result.success(saved);
    }

    @Transactional
    @Override
    public Result<TechnicalTicket, ApplicationError> handle(ModifyTicketStatus command) {
        var found = technicalTicketRepository.findById(command.ticketId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("TechnicalTicket", command.ticketId().uuid()));
        }
        var ticket = found.get();
        ticket.modifyStatus(command);
        var saved = technicalTicketRepository.save(ticket);
        return Result.success(saved);
    }

    @Transactional
    @Override
    public Result<MaintenanceLog, ApplicationError> handle(RegisterMaintenanceCompletion command) {
        var log = new MaintenanceLog(command);
        var saved = maintenanceLogRepository.save(log);
        return Result.success(saved);
    }

    @Transactional
    @Override
    public Result<TechnicalTicket, ApplicationError> handle(RequestUpdateMaintenanceStatus command) {
        var found = technicalTicketRepository.findById(command.ticketId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("TechnicalTicket", command.ticketId().uuid()));
        }
        var ticket = found.get();
        ticket.requestStatusUpdate(command);
        var saved = technicalTicketRepository.save(ticket);
        return Result.success(saved);
    }

    @Transactional
    @Override
    public Result<TechnicalTicket, ApplicationError> handle(UpdateMaintenanceStatus command) {
        var found = technicalTicketRepository.findById(command.ticketId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("TechnicalTicket", command.ticketId().uuid()));
        }
        var ticket = found.get();
        ticket.updateMaintenanceStatus(command);
        var saved = technicalTicketRepository.save(ticket);
        return Result.success(saved);
    }

    @Transactional
    @Override
    public Result<String, ApplicationError> handle(DecommissionEquipment command) {
        eventPublisher.publishEvent(new EquipmentDecommissionedEvent(command.equipmentId()));
        return Result.success(command.equipmentId());
    }

    @Transactional
    @Override
    public Result<String, ApplicationError> handle(RecommendEquipmentTransfer command) {
        eventPublisher.publishEvent(new EquipmentTransferRecommendedEvent(command.equipmentId(), command.reason()));
        return Result.success(command.equipmentId());
    }
}
