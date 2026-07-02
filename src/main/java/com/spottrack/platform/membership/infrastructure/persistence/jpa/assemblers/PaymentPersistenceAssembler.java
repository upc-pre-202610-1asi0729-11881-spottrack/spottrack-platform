package com.spottrack.platform.membership.infrastructure.persistence.jpa.assemblers;

import com.spottrack.platform.membership.domain.model.aggregates.Payment;
import com.spottrack.platform.membership.domain.model.valueobjects.PaymentId;
import com.spottrack.platform.membership.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;

import java.util.UUID;
import java.util.Optional;

public final class PaymentPersistenceAssembler {

    private PaymentPersistenceAssembler() {}

    public static Payment toDomainFromPersistence(PaymentPersistenceEntity entity) {
        var pendingRegistrationId = Optional.ofNullable(entity.getPendingRegistrationId())
                .map(UUID::fromString)
                .orElse(null);
        return new Payment(
                entity.getId(),
                new PaymentId(UUID.fromString(entity.getPaymentId())),
                entity.getUserId(),
                pendingRegistrationId,
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
        entity.setPendingRegistrationId(
                payment.getPendingRegistrationId() != null
                        ? payment.getPendingRegistrationId().toString()
                        : null);
        entity.setMembershipTier(payment.getMembershipTier());
        entity.setAmount(payment.getAmount());
        entity.setStatus(payment.getStatus());
        entity.setGatewayTransactionId(payment.getGatewayTransactionId());
        return entity;
    }
}
