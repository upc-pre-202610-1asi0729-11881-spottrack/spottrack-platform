package com.spottrack.platform.reservation.interfaces.rest.controllers;

import com.spottrack.platform.reservation.application.commandServices.ReservationRequestCommandService;
import com.spottrack.platform.reservation.domain.model.aggregates.ReservationRequest;
import com.spottrack.platform.reservation.domain.model.commands.RequestEquipmentStatusChangeToAvailable;
import com.spottrack.platform.reservation.domain.model.valueobjects.ReservationRequestId;
import com.spottrack.platform.reservation.interfaces.rest.resources.RequestAlternativeEquipmentResource;
import com.spottrack.platform.reservation.interfaces.rest.resources.ReservationRequestResource;
import com.spottrack.platform.reservation.interfaces.rest.resources.SubmitRequestOccupyEquipmentResource;
import com.spottrack.platform.reservation.interfaces.rest.transform.RequestAlternativeEquipmentCommandFromResourceAssembler;
import com.spottrack.platform.reservation.interfaces.rest.transform.ReservationRequestResourceFromEntityAssembler;
import com.spottrack.platform.reservation.interfaces.rest.transform.SubmitRequestOccupyEquipmentCommandFromResourceAssembler;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation-requests")
@Tag(name = "Reservation Requests")
public class ReservationRequestsController {

    private final ReservationRequestCommandService commandService;

    public ReservationRequestsController(ReservationRequestCommandService commandService) {
        this.commandService = commandService;
    }

    /**
     * POST /api/v1/reservation-requests
     * Client submits a request to occupy a specific piece of equipment.
     * Entry point of the standard (non-express) reservation flow.
     * Returns 201 with the created ReservationRequest.
     */
    @PostMapping
    public ResponseEntity<?> submitRequest(@RequestBody SubmitRequestOccupyEquipmentResource resource) {
        var command = SubmitRequestOccupyEquipmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<ReservationRequest, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(ReservationRequestResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<ReservationRequest, ApplicationError> f ->
                    ResponseEntity.badRequest().body(f.error());
        };
    }

    /**
     * PATCH /api/v1/reservation-requests/{id}/alternative
     * Client requests a different piece of equipment.
     * Only valid when the request is in SUBMITTED state — enforced by the aggregate.
     * Returns 200 with the updated request, or 404 if not found.
     */
    @PatchMapping("/{id}/alternative")
    public ResponseEntity<?> requestAlternative(@PathVariable String id, @RequestBody RequestAlternativeEquipmentResource resource) {
        var command = RequestAlternativeEquipmentCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<ReservationRequest, ApplicationError> s ->
                    ResponseEntity.ok(ReservationRequestResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<ReservationRequest, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }

    /**
     * PATCH /api/v1/reservation-requests/{id}/release
     * Signals that the equipment should be released back to AVAILABLE.
     * Triggered at the end or cancellation of a reservation.
     * Returns 200 with the updated request, or 404 if not found.
     */
    @PatchMapping("/{id}/release")
    public ResponseEntity<?> releaseEquipment(@PathVariable String id) {
        var command = new RequestEquipmentStatusChangeToAvailable(new ReservationRequestId(id));
        var result = commandService.handle(command);
        return switch (result) {
            case Result.Success<ReservationRequest, ApplicationError> s ->
                    ResponseEntity.ok(ReservationRequestResourceFromEntityAssembler.toResourceFromEntity(s.value()));
            case Result.Failure<ReservationRequest, ApplicationError> f ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(f.error());
        };
    }
}
