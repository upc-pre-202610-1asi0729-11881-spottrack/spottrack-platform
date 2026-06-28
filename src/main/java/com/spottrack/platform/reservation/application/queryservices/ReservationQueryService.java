package com.spottrack.platform.reservation.application.queryservices;

import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.domain.model.queries.GetAllReservationsQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationQueryService {
    List<Reservation> handle(GetAllReservationsQuery query);
}
