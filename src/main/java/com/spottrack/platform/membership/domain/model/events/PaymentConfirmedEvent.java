package com.spottrack.platform.membership.domain.model.events;

import com.spottrack.platform.membership.domain.model.aggregates.Payment;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipTier;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;

import java.util.UUID;

public record PaymentConfirmedEvent(
        Long id,
        UUID paymentId,
        Long userId,
        UUID pendingRegistrationId,
        MembershipTier membershipTier,
        Money amount,
        String gatewayTransactionId
) {
    public static PaymentConfirmedEvent from(Payment payment) {
        return new PaymentConfirmedEvent(
                payment.getId(),
                payment.getPaymentId().uuid(),
                payment.getUserId(),
                payment.getPendingRegistrationId(),
                payment.getMembershipTier(),
                payment.getAmount(),
                payment.getGatewayTransactionId()
        );
    }
}
