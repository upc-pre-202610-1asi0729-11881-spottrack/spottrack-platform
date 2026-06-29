package com.spottrack.platform.payment.application.commandservices;

import com.spottrack.platform.payment.domain.model.aggregates.Payment;
import com.spottrack.platform.payment.domain.model.commands.ConfirmPaymentCommand;
import com.spottrack.platform.payment.domain.model.commands.FailPaymentCommand;
import com.spottrack.platform.payment.domain.model.commands.RecordPaymentCommand;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;

public interface PaymentCommandService {
    Result<Payment, ApplicationError> handle(RecordPaymentCommand command);
    Result<Payment, ApplicationError> handle(ConfirmPaymentCommand command);
    Result<Payment, ApplicationError> handle(FailPaymentCommand command);
}
