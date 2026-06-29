package com.spottrack.platform.payment.domain.model.commands;

import com.spottrack.platform.payment.domain.model.valueobjects.PaymentId;

public record FailPaymentCommand(PaymentId paymentId) {
    public FailPaymentCommand {
        if (paymentId == null) throw new IllegalArgumentException("payment.error.paymentId.notNull");
    }
}
