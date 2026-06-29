package com.spottrack.platform.membership.domain.model.aggregates;

import com.spottrack.platform.membership.domain.model.commands.CreateMembershipCommand;
import com.spottrack.platform.membership.domain.model.commands.RenewMembershipCommand;
import com.spottrack.platform.membership.domain.model.commands.UpgradeMembershipPlanCommand;
import com.spottrack.platform.membership.domain.model.events.GymMembershipActivatedEvent;
import com.spottrack.platform.membership.domain.model.events.GymMembershipRenewedEvent;
import com.spottrack.platform.membership.domain.model.events.MembershipCancelledEvent;
import com.spottrack.platform.membership.domain.model.events.MembershipCreatedEvent;
import com.spottrack.platform.membership.domain.model.events.MembershipSuspendedEvent;
import com.spottrack.platform.membership.domain.model.events.PlanUpgradedEvent;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipId;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipPeriod;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipStatus;
import com.spottrack.platform.membership.domain.model.valueobjects.MembershipId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;
import lombok.Getter;
import lombok.Setter;

public class Membership extends AbstractDomainAggregateRoot<Membership> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private MembershipId membershipId;

    @Getter
    private Long clientId;

    @Getter
    private MembershipId membershipType;

    @Getter
    private Money price;

    @Getter
    private MembershipPeriod membershipPeriod;

    @Getter
    private MembershipStatus status;

    public Membership(Long id, MembershipId membershipId, Long clientId, MembershipType membershipType,
                      Money price, MembershipPeriod membershipPeriod, MembershipStatus status) {
        this.id = id;
        this.membershipId = membershipId;
        this.clientId = clientId;
        this.membershipType = membershipType;
        this.price = price;
        this.membershipPeriod = membershipPeriod;
        this.status = status;
    }

    public Membership(CreateMembershipCommand command) {
        this(null,
                new MembershipId(),
                command.clientId(),
                command.membershipType(),
                command.price(),
                new MembershipPeriod(command.startDate(), command.endDate()),
                MembershipStatus.PENDING_ACTIVATION);
    }

    public void activate() {
        if (this.status != MembershipStatus.PENDING_ACTIVATION && this.status != MembershipStatus.SUSPENDED) {
            throw new IllegalStateException("membership.error.activate.invalidStatus");
        }
        this.status = MembershipStatus.ACTIVE;
        registerDomainEvent(GymMembershipActivatedEvent.from(this));
    }

    public void cancel() {
        if (this.status == MembershipStatus.CANCELLED) {
            throw new IllegalStateException("membership.error.cancel.alreadyCancelled");
        }
        this.status = MembershipStatus.CANCELLED;
        registerDomainEvent(MembershipCancelledEvent.from(this));
    }

    public void suspend() {
        if (this.status != MembershipStatus.ACTIVE) {
            throw new IllegalStateException("membership.error.suspend.notActive");
        }
        this.status = MembershipStatus.SUSPENDED;
        registerDomainEvent(MembershipSuspendedEvent.from(this));
    }

    public void upgradePlan(UpgradeMembershipPlanCommand command) {
        this.membershipType = command.newMembershipType();
        registerDomainEvent(PlanUpgradedEvent.from(this));
    }

    public void renew(RenewMembershipCommand command) {
        this.membershipPeriod = new MembershipPeriod(command.startDate(), command.endDate());
        this.status = MembershipStatus.ACTIVE;
        registerDomainEvent(GymMembershipRenewedEvent.from(this));
    }

    public void onCreated() {
        registerDomainEvent(MembershipCreatedEvent.from(this));
    }
}
