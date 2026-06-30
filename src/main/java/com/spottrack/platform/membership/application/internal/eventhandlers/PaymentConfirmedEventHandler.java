package com.spottrack.platform.membership.application.internal.eventhandlers;

import com.spottrack.platform.membership.application.commandservices.MembershipCommandService;
import com.spottrack.platform.membership.domain.model.aggregates.Membership;
import com.spottrack.platform.membership.domain.model.commands.ActivateMembershipCommand;
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
        try {
            log.info("Processing PaymentConfirmedEvent: paymentId={}, userId={}, tier={}, amount={}",
                    event.paymentId(), event.userId(), event.membershipTier(), event.amount());

            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(30);

            var createCommand = new CreateMembershipCommand(
                    event.userId(),
                    event.membershipTier(),
                    event.amount(),
                    startDate,
                    endDate
            );

            var createResult = membershipCommandService.handle(createCommand);

            if (createResult instanceof Result.Failure<?, ?> failure && failure.error() instanceof ApplicationError error) {
                log.error("Failed to create membership after payment {} confirmed for user {}: {}",
                        event.paymentId(), event.userId(), error.message());
                return;
            }

            var membership = ((Result.Success<Membership, ?>) createResult).value();
            log.info("Membership {} created for user {}, activating now", membership.getMembershipId(), event.userId());

            var activateCommand = new ActivateMembershipCommand(membership.getMembershipId());
            var activateResult = membershipCommandService.handle(activateCommand);

            if (activateResult instanceof Result.Failure<?, ?> failure && failure.error() instanceof ApplicationError error) {
                log.error("Membership created but activation failed for payment {} user {}: {}",
                        event.paymentId(), event.userId(), error.message());
            } else {
                log.info("Membership {} activated for user {} after payment {} confirmed",
                        membership.getMembershipId(), event.userId(), event.paymentId());
            }
        } catch (Exception e) {
            log.error("Unexpected error in PaymentConfirmedEventHandler for payment {} user {}: {} - {}",
                    event.paymentId(), event.userId(), e.getClass().getSimpleName(), e.getMessage(), e);
        }
    }
}
