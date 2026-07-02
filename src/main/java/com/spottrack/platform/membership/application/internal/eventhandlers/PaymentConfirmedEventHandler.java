package com.spottrack.platform.membership.application.internal.eventhandlers;

import com.spottrack.platform.iam.interfaces.acl.IamContextFacade;
import com.spottrack.platform.membership.domain.model.aggregates.Membership;
import com.spottrack.platform.membership.domain.model.commands.CreateMembershipCommand;
import com.spottrack.platform.membership.domain.model.commands.RenewMembershipCommand;
import com.spottrack.platform.membership.domain.model.events.PaymentConfirmedEvent;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipId;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipStatus;
import com.spottrack.platform.membership.domain.repositories.MembershipRepository;
import com.spottrack.platform.profiles.interfaces.acl.ProfilesContextFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class PaymentConfirmedEventHandler {

    private final MembershipRepository membershipRepository;
    private final IamContextFacade iamContextFacade;
    private final ProfilesContextFacade profilesContextFacade;

    PaymentConfirmedEventHandler(MembershipRepository membershipRepository,
                                 IamContextFacade iamContextFacade,
                                 ProfilesContextFacade profilesContextFacade) {
        this.membershipRepository = membershipRepository;
        this.iamContextFacade = iamContextFacade;
        this.profilesContextFacade = profilesContextFacade;
    }

    @EventListener
    public void on(PaymentConfirmedEvent event) {
        log.info("Processing PaymentConfirmedEvent: paymentId={}, userId={}, pendingRegistrationId={}, tier={}, amount={}",
                event.paymentId(), event.userId(), event.pendingRegistrationId(),
                event.membershipTier(), event.amount());
        try {
            if (event.membershipId() != null) {
                handleDebtPayment(event);
            } else if (event.userId() != null) {
                handleExistingUserPayment(event);
            } else if (event.pendingRegistrationId() != null) {
                handleBusinessRegistrationPayment(event);
            } else {
                log.error("PaymentConfirmedEvent for payment {} has no routing key — skipping",
                        event.paymentId());
            }
        } catch (Exception e) {
            log.error("Error in PaymentConfirmedEventHandler for payment {} : {}",
                    event.paymentId(), e.getMessage(), e);
        }
    }

    private void handleDebtPayment(PaymentConfirmedEvent event) {
        var membership = membershipRepository.findByMembershipId(new MembershipId(event.membershipId()));
        if (membership.isEmpty()) {
            log.error("Debt payment confirmed for unknown membershipId={} (payment {}) — skipping",
                    event.membershipId(), event.paymentId());
            return;
        }
        var m = membership.get();
        if (m.getStatus() == MembershipStatus.ACTIVE) {
            log.info("Membership {} already ACTIVE — skipping debt renewal (idempotent, payment {})",
                    event.membershipId(), event.paymentId());
            return;
        }
        if (m.getStatus() != MembershipStatus.SUSPENDED) {
            log.warn("Membership {} is in status {} — cannot renew via debt payment (payment {}). Manual review required.",
                    event.membershipId(), m.getStatus(), event.paymentId());
            return;
        }
        var today = LocalDate.now();
        var command = new RenewMembershipCommand(new MembershipId(event.membershipId()), today, today.plusDays(30));
        m.renew(command);
        membershipRepository.save(m);
        log.info("Membership {} renewed after debt payment (clientId={}, payment {})",
                event.membershipId(), m.getClientId(), event.paymentId());
    }

    private void handleExistingUserPayment(PaymentConfirmedEvent event) {
        createAndActivateMembership(event.userId(), event);
    }

    private void handleBusinessRegistrationPayment(PaymentConfirmedEvent event) {
        var provisioned = iamContextFacade.consumePendingRegistration(event.pendingRegistrationId());
        if (provisioned.isEmpty()) {
            log.error("PendingRegistration {} not found or expired — cannot provision account for payment {}",
                    event.pendingRegistrationId(), event.paymentId());
            return;
        }

        var dto = provisioned.get();
        log.info("Provisioning account for pendingRegistrationId={}, resolved userId={}",
                event.pendingRegistrationId(), dto.userId());

        profilesContextFacade.provisionAdminProfile(dto.userId(), dto.email());

        profilesContextFacade.provisionBusinessProfile(
                dto.userId(),
                dto.companyName(), dto.ruc(), dto.legalStructure(),
                dto.companyPhone(), dto.companyEmail(),
                dto.streetAddress(), dto.city(), dto.district()
        );

        createAndActivateMembership(dto.userId(), event);
    }

    private void createAndActivateMembership(Long userId, PaymentConfirmedEvent event) {
        var existing = membershipRepository.findByClientId(userId);
        boolean hasActiveMembership = existing.stream()
                .anyMatch(m -> m.getStatus() == MembershipStatus.ACTIVE);

        if (hasActiveMembership) {
            log.info("Active membership already exists for userId {}, skipping creation (payment {})",
                    userId, event.paymentId());
            return;
        }

        var startDate = LocalDate.now();
        var endDate = startDate.plusDays(30);

        var command = new CreateMembershipCommand(
                userId,
                event.membershipTier(),
                event.amount(),
                startDate,
                endDate
        );

        Membership membership = membershipRepository.save(new Membership(command));
        log.info("Membership {} created for userId {}, activating", membership.getMembershipId(), userId);

        membership.activate();
        membershipRepository.save(membership);

        log.info("Membership {} is now ACTIVE for userId {} (payment {})",
                membership.getMembershipId(), userId, event.paymentId());
    }
}
