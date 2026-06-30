package com.spottrack.platform.membership.application.internal.eventhandlers;

import com.spottrack.platform.membership.application.commandservices.MembershipCommandService;
import com.spottrack.platform.membership.domain.model.commands.CreateMembershipCommand;
import com.spottrack.platform.membership.domain.model.events.PaymentConfirmedEvent;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class PaymentConfirmedEventHandler {

    private final MembershipCommandService membershipCommandService;

    PaymentConfirmedEventHandler(MembershipCommandService membershipCommandService) {
        this.membershipCommandService = membershipCommandService;
    }

    @EventListener
    public void on(PaymentConfirmedEvent event) {
        var startDate = LocalDate.now();
        var endDate = startDate.plusDays(30);

        var command = new CreateMembershipCommand(
                event.userId(),
                event.membershipTier(),
                event.amount(),
                startDate,
                endDate
        );

        var result = membershipCommandService.handle(command);

        if (result instanceof Result.Failure<?, ?> failure && failure.error() instanceof ApplicationError error) {
            log.warn("Failed to create membership after payment {} confirmed for user {}: {}",
                    event.paymentId(), event.userId(), error.message());
        } else {
            log.info("Membership created for user {} after payment {} confirmed", event.userId(), event.paymentId());
        }
    }
}
