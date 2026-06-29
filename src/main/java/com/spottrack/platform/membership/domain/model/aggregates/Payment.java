package com.spottrack.platform.membership.domain.model.aggregates;

import com.spottrack.platform.membership.domain.model.valueobjects.MembershipTier;
import com.spottrack.platform.membership.domain.model.valueobjects.PaymentId;
import com.spottrack.platform.membership.domain.model.valueobjects.PaymentStatus;
import com.spottrack.platform.membership.domain.model.valueobjects.UserId;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;

public class Payment {
  private PaymentId paymentId;
  private PaymentStatus paymentStatus;
  private MembershipTier membershipTier;
  private Money amount;
  private UserId userId;
  private String gatewayTransactionId;

  public Payment(PaymentId paymentId, PaymentStatus paymentStatus, MembershipTier membershipTier, Money amount,
      UserId userId, String gatewayTransactionId) {
    this.amount = amount;
    this.membershipTier = membershipTier;
    this.paymentStatus = paymentStatus;
    this.paymentId = paymentId;
    this.userId = userId;
    this.gatewayTransactionId = gatewayTransactionId;
  }
}
