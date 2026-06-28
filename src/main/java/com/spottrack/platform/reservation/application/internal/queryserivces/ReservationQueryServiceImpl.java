package com.spottrack.platform.reservation.application.internal.queryserivces;


import com.spottrack.platform.reservation.application.queryservices.ReservationQueryService;
import com.spottrack.platform.reservation.domain.model.aggregates.Reservation;
import com.spottrack.platform.reservation.domain.model.queries.GetAllReservationsQuery;
import com.spottrack.platform.reservation.domain.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class ReservationQueryServiceImpl implements ReservationQueryService {
    private final ReservationRepository reservationRepository;
    public ReservationQueryServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    @Override
    public List<Reservation> handle(GetAllReservationsQuery query) {
        var list = reservationRepository.findAll();
        return list;
    }
}
