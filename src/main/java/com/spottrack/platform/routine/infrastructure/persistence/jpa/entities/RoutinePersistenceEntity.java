package com.spottrack.platform.routine.infrastructure.persistence.jpa.entities;

import com.spottrack.platform.routine.infrastructure.persistence.jpa.embeddables.ExerciseBlockPersistenceEmbeddable;
import com.spottrack.platform.routine.infrastructure.persistence.jpa.embeddables.RoutineNamePersistenceEmbeddable;
import com.spottrack.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routines")
public class RoutinePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Embedded
    private RoutineNamePersistenceEmbeddable routineName;

    @ElementCollection
    @CollectionTable(name = "exercise_blocks", joinColumns = @JoinColumn(name = "routine_id"))
    private List<ExerciseBlockPersistenceEmbeddable> exerciseBlocks = new ArrayList<>();

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public RoutineNamePersistenceEmbeddable getRoutineName() { return routineName; }
    public void setRoutineName(RoutineNamePersistenceEmbeddable routineName) { this.routineName = routineName; }

    public List<ExerciseBlockPersistenceEmbeddable> getExerciseBlocks() { return exerciseBlocks; }
    public void setExerciseBlocks(List<ExerciseBlockPersistenceEmbeddable> exerciseBlocks) { this.exerciseBlocks = exerciseBlocks; }
}
