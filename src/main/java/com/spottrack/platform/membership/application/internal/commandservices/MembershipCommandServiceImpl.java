package com.spottrack.platform.membership.application.internal.commandservices;

import com.spottrack.platform.membership.application.commandservices.MembershipCommandService;
import com.spottrack.platform.membership.domain.model.aggregates.Membership;
import com.spottrack.platform.membership.domain.model.commands.ActivateMembershipCommand;
import com.spottrack.platform.membership.domain.model.commands.CancelMembershipCommand;
import com.spottrack.platform.membership.domain.model.commands.CreateMembershipCommand;
import com.spottrack.platform.membership.domain.model.commands.RenewMembershipCommand;
import com.spottrack.platform.membership.domain.model.commands.SuspendMembershipCommand;
import com.spottrack.platform.membership.domain.model.commands.UpgradeMembershipPlanCommand;
import com.spottrack.platform.membership.domain.repositories.MembershipRepository;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class MembershipCommandServiceImpl implements MembershipCommandService {

    private final MembershipRepository membershipRepository;

    public MembershipCommandServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Result<Membership, ApplicationError> handle(CreateMembershipCommand command) {
        try {
            var membership = new Membership(command);
            var saved = membershipRepository.save(membership);
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Membership", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Membership creation", e.getMessage()));
        }
    }

    @Override
    public Result<Membership, ApplicationError> handle(CancelMembershipCommand command) {
        try {
            var membership = membershipRepository.findByMembershipId(command.membershipId());
            if (membership.isEmpty()) {
                return Result.failure(ApplicationError.notFound("Membership", command.membershipId().uuid().toString()));
            }
            membership.get().cancel();
            var saved = membershipRepository.save(membership.get());
            return Result.success(saved);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return Result.failure(ApplicationError.businessRuleViolation("Membership.cancel", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Membership cancellation", e.getMessage()));
        }
    }

    @Override
    public Result<Membership, ApplicationError> handle(UpgradeMembershipPlanCommand command) {
        try {
            var membership = membershipRepository.findByMembershipId(command.membershipId());
            if (membership.isEmpty()) {
                return Result.failure(ApplicationError.notFound("Membership", command.membershipId().uuid().toString()));
            }
            membership.get().upgradePlan(command);
            var saved = membershipRepository.save(membership.get());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Membership", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Membership plan upgrade", e.getMessage()));
        }
    }

    @Override
    public Result<Membership, ApplicationError> handle(ActivateMembershipCommand command) {
        try {
            var membership = membershipRepository.findByMembershipId(command.membershipId());
            if (membership.isEmpty()) {
                return Result.failure(ApplicationError.notFound("Membership", command.membershipId().uuid().toString()));
            }
            membership.get().activate();
            var saved = membershipRepository.save(membership.get());
            return Result.success(saved);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("Membership.activate", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Membership activation", e.getMessage()));
        }
    }

    @Override
    public Result<Membership, ApplicationError> handle(SuspendMembershipCommand command) {
        try {
            var membership = membershipRepository.findByMembershipId(command.membershipId());
            if (membership.isEmpty()) {
                return Result.failure(ApplicationError.notFound("Membership", command.membershipId().uuid().toString()));
            }
            membership.get().suspend();
            var saved = membershipRepository.save(membership.get());
            return Result.success(saved);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation("Membership.suspend", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Membership suspension", e.getMessage()));
        }
    }

    @Override
    public Result<Membership, ApplicationError> handle(RenewMembershipCommand command) {
        try {
            var membership = membershipRepository.findByMembershipId(command.membershipId());
            if (membership.isEmpty()) {
                return Result.failure(ApplicationError.notFound("Membership", command.membershipId().uuid().toString()));
            }
            membership.get().renew(command);
            var saved = membershipRepository.save(membership.get());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Membership", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Membership renewal", e.getMessage()));
        }
    }
}
