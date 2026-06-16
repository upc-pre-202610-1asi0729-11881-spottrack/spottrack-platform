package com.spottrack.platform.reservation.interfaces.rest.controllers;

import com.spottrack.platform.reservation.application.commandServices.ReservationCommandService;
import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.domain.model.commands.CancelReservation;
import com.spottrack.platform.reservation.domain.model.commands.EndReservation;
import com.spottrack.platform.reservation.domain.model.commands.StartReservationTimer;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationId;
import com.spottrack.platform.reservation.interfaces.rest.resources.EndReservationCommandResource;
import com.spottrack.platform.reservation.interfaces.rest.resources.InitiateExpressReservationResource;
import com.spottrack.platform.reservation.interfaces.rest.resources.ReservationResource;
import com.spottrack.platform.reservation.interfaces.rest.resources.StartReservationTimerResource;
import com.spottrack.platform.reservation.interfaces.rest.transform.EndReservationCommandFromResourceAssembler;
import com.spottrack.platform.reservation.interfaces.rest.transform.InitiateExpressReservationCommandFromResourceAssembler;
import com.spottrack.platform.reservation.interfaces.rest.transform.ReservationResourceFromEntityAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name = "Reservations")
public class ReservationsController {

    private final ReservationCommandService commandService;

    public ReservationsController(ReservationCommandService commandService) {
        this.commandService = commandService;
    }

    /**
     * POST /api/v1/reservations
     * Initiates an express reservation — client skips the request flow and directly reserves equipment.
     * Returns 201 with the created Reservation, or 400 on validation failure.
     */
    @PostMapping("/reserve")
    public ResponseEntity<?> initiateExpressReservation(@RequestBody InitiateExpressReservationResource resource) {
        var command = InitiateExpressReservationCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Reservation, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(ReservationResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Reservation, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    /**
     * PATCH /api/v1/reservations/{id}/timer
     * Starts the countdown timer for an active reservation.
     * Returns 200 with the updated Reservation, or 404 if the reservation doesn't exist.
     */
    @PatchMapping("/{id}/timer")
    public ResponseEntity<?> startTimer(@PathVariable String id, @RequestBody StartReservationTimerResource resource) {
        var command = new StartReservationTimer(new ReservationId(id), resource.durationMinutes());
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Reservation, ApplicationError> s ->
                    ResponseEntity.ok(ReservationResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Reservation, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    /**
     * DELETE /api/v1/reservations/{id}
     * Cancels an active reservation.
     * Returns 200 with the cancelled Reservation, or 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable String id) {
        var command = new CancelReservation(new ReservationId(id));
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Reservation, ApplicationError> s ->
                    ResponseEntity.ok(ReservationResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Reservation, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    /**
     * PATCH /api/v1/reservations/{id}/end
     * Client explicitly ends the reservation before the timer runs out.
     * Returns 200 with the ended Reservation, or 404 if not found.
     */
    @PatchMapping("/{id}/end")
    public ResponseEntity<?> endReservation(@PathVariable String id, EndReservationCommandResource resource) {
        var command = EndReservationCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<Reservation, ApplicationError> s ->
                    ResponseEntity.ok(ReservationResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<Reservation, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }
}
