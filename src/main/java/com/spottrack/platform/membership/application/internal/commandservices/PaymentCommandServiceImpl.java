package com.spottrack.platform.membership.application.internal.commandservices;

import com.spottrack.platform.membership.application.commandservices.PaymentCommandService;
import com.spottrack.platform.membership.domain.model.aggregates.Payment;
import com.spottrack.platform.membership.domain.model.commands.ConfirmPaymentCommand;
import com.spottrack.platform.membership.domain.model.commands.FailPaymentCommand;
import com.spottrack.platform.membership.domain.model.commands.PayMembershipCommand;
import com.spottrack.platform.membership.domain.repositories.PaymentRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;

    public PaymentCommandServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Result<Payment, ApplicationError> handle(PayMembershipCommand command) {
        try {
            var payment = new Payment(command);
            var saved = paymentRepository.save(payment);

            // TODO: initiate Stripe payment session here using saved.getPaymentId()
            // On success webhook -> call handle(ConfirmPaymentCommand)
            // On failure webhook -> call handle(FailPaymentCommand)

            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Payment", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Payment recording", e.getMessage()));
        }
    }

    @Override
    public Result<Payment, ApplicationError> handle(ConfirmPaymentCommand command) {
        try {
            var payment = paymentRepository.findByPaymentId(command.paymentId());
            if (payment.isEmpty()) {
                return Result.failure(ApplicationError.notFound("Payment", command.paymentId().uuid().toString()));
            }
            payment.get().confirm(command.gatewayTransactionId());
            var saved = paymentRepository.save(payment.get());
            return Result.success(saved);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("Payment.confirm", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Payment confirmation", e.getMessage()));
        }
    }

    @Override
    public Result<Payment, ApplicationError> handle(FailPaymentCommand command) {
        try {
            var payment = paymentRepository.findByPaymentId(command.paymentId());
            if (payment.isEmpty()) {
                return Result.failure(ApplicationError.notFound("Payment", command.paymentId().uuid().toString()));
            }
            payment.get().fail();
            var saved = paymentRepository.save(payment.get());
            return Result.success(saved);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("Payment.fail", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Payment failure recording", e.getMessage()));
        }
    }
}
