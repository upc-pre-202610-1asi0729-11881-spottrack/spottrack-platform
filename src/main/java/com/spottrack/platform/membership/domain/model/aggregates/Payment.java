package com.spottrack.platform.membership.domain.model.aggregates;

import com.spottrack.platform.membership.domain.model.commands.PayMembershipCommand;
import com.spottrack.platform.membership.domain.model.events.MembershipPayedEvent;
import com.spottrack.platform.membership.domain.model.events.PaymentConfirmedEvent;
import com.spottrack.platform.membership.domain.model.events.PaymentFailedEvent;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipTier;
import com.spottrack.platform.membership.domain.model.valueobjects.PaymentId;
import com.spottrack.platform.membership.domain.model.valueobjects.PaymentStatus;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;
import lombok.Getter;
import lombok.Setter;

public class Payment extends AbstractDomainAggregateRoot<Payment> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private PaymentId paymentId;

    @Getter
    private Long userId;

    @Getter
    private MembershipTier membershipTier;

    @Getter
    private Money amount;

    @Getter
    private PaymentStatus status;

    @Getter
    private String gatewayTransactionId;

    public Payment(Long id, PaymentId paymentId, Long userId, MembershipTier membershipTier,
                   Money amount, PaymentStatus status, String gatewayTransactionId) {
        this.id = id;
        this.paymentId = paymentId;
        this.userId = userId;
        this.membershipTier = membershipTier;
        this.amount = amount;
        this.status = status;
        this.gatewayTransactionId = gatewayTransactionId;
    }

    public Payment(PayMembershipCommand command) {
        this(null, new PaymentId(), command.userId(), command.membershipTier(),
                command.amount(), PaymentStatus.PENDING, null);
    }

    public void confirm(String gatewayTransactionId) {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("membership.error.payment.confirm.notPending");
        }
        this.gatewayTransactionId = gatewayTransactionId;
        this.status = PaymentStatus.CONFIRMED;
        registerDomainEvent(PaymentConfirmedEvent.from(this));
    }

    public void fail() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("membership.error.payment.fail.notPending");
        }
        this.status = PaymentStatus.FAILED;
        registerDomainEvent(PaymentFailedEvent.from(this));
    }

    public void onCreated() {
        registerDomainEvent(MembershipPayedEvent.from(this));
    }
}
