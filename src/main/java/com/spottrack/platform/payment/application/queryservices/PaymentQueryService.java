package com.spottrack.platform.payment.application.queryservices;

import com.spottrack.platform.payment.domain.model.aggregates.Payment;
import com.spottrack.platform.payment.domain.model.queries.GetPaymentByIdQuery;
import com.spottrack.platform.payment.domain.model.queries.GetPaymentsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface PaymentQueryService {
    Optional<Payment> handle(GetPaymentByIdQuery query);
    List<Payment> handle(GetPaymentsByUserIdQuery query);
}
