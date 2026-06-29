package com.spottrack.platform.payment.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.payment.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentPersistenceRepository extends JpaRepository<PaymentPersistenceEntity, Long> {
    Optional<PaymentPersistenceEntity> findByPaymentId(String paymentId);
    List<PaymentPersistenceEntity> findByUserId(Long userId);
}
