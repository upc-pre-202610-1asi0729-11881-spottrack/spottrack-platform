package com.spottrack.platform.payment.domain.model.events;

import com.spottrack.platform.payment.domain.model.aggregates.Payment;

import java.util.UUID;

public record PaymentFailedEvent(
        Long id,
        UUID paymentId,
        Long userId
) {
    public static PaymentFailedEvent from(Payment payment) {
        return new PaymentFailedEvent(
                payment.getId(),
                payment.getPaymentId().uuid(),
                payment.getUserId()
        );
    }
}
