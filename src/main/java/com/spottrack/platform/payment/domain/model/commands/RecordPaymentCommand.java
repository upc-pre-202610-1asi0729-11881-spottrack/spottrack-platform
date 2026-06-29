package com.spottrack.platform.payment.domain.model.commands;

import com.spottrack.platform.membership.domain.model.valueobjects.MembershipTier;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;

public record RecordPaymentCommand(
        Long userId,
        MembershipTier membershipTier,
        Money amount
) {
    public RecordPaymentCommand {
        if (userId == null) throw new IllegalArgumentException("payment.error.userId.notNull");
        if (membershipTier == null) throw new IllegalArgumentException("payment.error.membershipTier.notNull");
        if (amount == null) throw new IllegalArgumentException("payment.error.amount.notNull");
    }
}
