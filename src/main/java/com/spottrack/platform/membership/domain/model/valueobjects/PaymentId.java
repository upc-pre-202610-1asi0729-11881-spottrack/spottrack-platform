package com.spottrack.platform.membership.domain.model.valueobjects;

import com.spottrack.platform.membership.domain.model.aggregates.Payment;

import java.util.UUID;

public record PaymentId(UUID uuid) {
    public  PaymentId {
        if (uuid == null){
            throw new IllegalArgumentException("payment.error.paymentId.notNull");
        }
    }
}
