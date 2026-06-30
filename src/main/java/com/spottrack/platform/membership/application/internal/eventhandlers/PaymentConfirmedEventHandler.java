package com.spottrack.platform.membership.application.internal.eventhandlers;

import com.spottrack.platform.membership.domain.model.aggregates.Membership;
import com.spottrack.platform.membership.domain.model.commands.CreateMembershipCommand;
import com.spottrack.platform.membership.domain.model.events.PaymentConfirmedEvent;
import com.spottrack.platform.membership.domain.repositories.MembershipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class PaymentConfirmedEventHandler {

    private final MembershipRepository membershipRepository;

    PaymentConfirmedEventHandler(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @EventListener
    public void on(PaymentConfirmedEvent event) {
        try {
            log.info("Processing PaymentConfirmedEvent: paymentId={}, userId={}, tier={}, amount={}",
                    event.paymentId(), event.userId(), event.membershipTier(), event.amount());

            var startDate = LocalDate.now();
            var endDate = startDate.plusDays(30);

            var command = new CreateMembershipCommand(
                    event.userId(),
                    event.membershipTier(),
                    event.amount(),
                    startDate,
                    endDate
            );

            // Create membership (initially PENDING_ACTIVATION per domain rules)
            Membership membership = membershipRepository.save(new Membership(command));
            log.info("Membership {} created for user {}, activating", membership.getMembershipId(), event.userId());

            // Activate immediately — payment is already confirmed, no further check needed
            membership.activate();
            membershipRepository.save(membership);

            log.info("Membership {} is now ACTIVE for user {} (payment {})",
                    membership.getMembershipId(), event.userId(), event.paymentId());

        } catch (Exception e) {
            log.error("Error in PaymentConfirmedEventHandler for payment {} user {}: {}",
                    event.paymentId(), event.userId(), e.getMessage(), e);
        }
    }
}
