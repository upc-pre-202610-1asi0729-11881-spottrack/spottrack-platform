package com.spottrack.platform.payment.domain.repositories;

import com.spottrack.platform.payment.domain.model.aggregates.Payment;
import com.spottrack.platform.payment.domain.model.valueobjects.PaymentId;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findByPaymentId(PaymentId paymentId);
    List<Payment> findByUserId(Long userId);
    Payment save(Payment payment);
}
