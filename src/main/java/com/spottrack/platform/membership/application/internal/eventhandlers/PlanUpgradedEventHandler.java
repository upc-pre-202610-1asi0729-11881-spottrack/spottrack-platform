package com.spottrack.platform.membership.application.internal.eventhandlers;

import com.spottrack.platform.membership.application.commandservices.MembershipCommandService;
import com.spottrack.platform.membership.domain.model.commands.ActivateMembershipCommand;
import com.spottrack.platform.membership.domain.model.events.PlanUpgradedEvent;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipId;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlanUpgradedEventHandler {

    private final MembershipCommandService membershipCommandService;

    PlanUpgradedEventHandler(MembershipCommandService membershipCommandService) {
        this.membershipCommandService = membershipCommandService;
    }

    @EventListener
    public void on(PlanUpgradedEvent event) {
        var command = new ActivateMembershipCommand(new MembershipId(event.membershipId()));
        var result = membershipCommandService.handle(command);

        if (result instanceof Result.Failure<?, ?> failure && failure.error() instanceof ApplicationError error) {
            log.warn("Failed to activate membership after plan upgrade for membership {}: {}",
                    event.membershipId(), error.message());
        }
    }
}
