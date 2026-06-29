package com.spottrack.platform.membership.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.membership.domain.model.aggregates.Payment;
import com.spottrack.platform.membership.domain.model.valueobjects.PaymentId;
import com.spottrack.platform.membership.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;

import java.util.UUID;

public final class PaymentPersistenceAssembler {

    private PaymentPersistenceAssembler() {}

    public static Payment toDomainFromPersistence(PaymentPersistenceEntity entity) {
        return new Payment(
                entity.getId(),
                new PaymentId(UUID.fromString(entity.getPaymentId())),
                entity.getUserId(),
                entity.getMembershipTier(),
                entity.getAmount(),
                entity.getStatus(),
                entity.getGatewayTransactionId()
        );
    }

    public static PaymentPersistenceEntity toPersistenceFromDomain(Payment payment) {
        var entity = new PaymentPersistenceEntity();
        entity.setId(payment.getId());
        entity.setPaymentId(payment.getPaymentId().uuid().toString());
        entity.setUserId(payment.getUserId());
        entity.setMembershipTier(payment.getMembershipTier());
        entity.setAmount(payment.getAmount());
        entity.setStatus(payment.getStatus());
        entity.setGatewayTransactionId(payment.getGatewayTransactionId());
        return entity;
    }
}
