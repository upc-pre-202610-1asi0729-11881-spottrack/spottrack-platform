package com.spottrack.platform.payment.domain.model.valueobjects;

import java.util.UUID;

public record PaymentId(UUID uuid) {
    public PaymentId() { this(UUID.randomUUID()); }

    public PaymentId {
        if (uuid == null) throw new IllegalArgumentException("payment.error.paymentId.notNull");
    }
}
