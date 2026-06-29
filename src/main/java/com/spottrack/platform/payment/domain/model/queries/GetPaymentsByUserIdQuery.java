package com.spottrack.platform.payment.domain.model.queries;

public record GetPaymentsByUserIdQuery(Long userId) {
    public GetPaymentsByUserIdQuery {
        if (userId == null) throw new IllegalArgumentException("payment.error.userId.notNull");
    }
}
