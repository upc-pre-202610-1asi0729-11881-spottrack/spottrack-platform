package com.spottrack.platform.payment.domain.model.commands;

import com.spottrack.platform.payment.domain.model.valueobjects.PaymentId;

public record ConfirmPaymentCommand(PaymentId paymentId, String gatewayTransactionId) {
    public ConfirmPaymentCommand {
        if (paymentId == null) throw new IllegalArgumentException("payment.error.paymentId.notNull");
        if (gatewayTransactionId == null || gatewayTransactionId.isBlank())
            throw new IllegalArgumentException("payment.error.gatewayTransactionId.blank");
    }
}
