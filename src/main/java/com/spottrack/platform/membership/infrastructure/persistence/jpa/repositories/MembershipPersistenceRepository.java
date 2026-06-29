package com.spottrack.platform.membership.infrastructure.persistence.jpa.repositories;

import com.spottrack.platform.membership.infrastructure.persistence.jpa.entities.MembershipPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipPersistenceRepository extends JpaRepository<MembershipPersistenceEntity, Long> {
    Optional<MembershipPersistenceEntity> findByMembershipId(String membershipId);
    List<MembershipPersistenceEntity> findByClientId(Long clientId);
    boolean existsByMembershipId(String membershipId);
}
