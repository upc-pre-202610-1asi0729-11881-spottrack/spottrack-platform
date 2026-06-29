package com.spottrack.platform.payment.domain.model.events;

import com.spottrack.platform.membership.domain.model.valueobjects.MembershipTier;
import com.spottrack.platform.payment.domain.model.aggregates.Payment;

import java.util.UUID;

public record PaymentRecordedEvent(
        Long id,
        UUID paymentId,
        Long userId,
        MembershipTier membershipTier
) {
    public static PaymentRecordedEvent from(Payment payment) {
        return new PaymentRecordedEvent(
                payment.getId(),
                payment.getPaymentId().uuid(),
                payment.getUserId(),
                payment.getMembershipTier()
        );
    }
}
