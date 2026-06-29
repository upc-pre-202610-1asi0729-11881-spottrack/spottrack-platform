package com.spottrack.platform.payment.domain.model.queries;

import com.spottrack.platform.payment.domain.model.valueobjects.PaymentId;

public record GetPaymentByIdQuery(PaymentId paymentId) {
    public GetPaymentByIdQuery {
        if (paymentId == null) throw new IllegalArgumentException("payment.error.paymentId.notNull");
    }
}
