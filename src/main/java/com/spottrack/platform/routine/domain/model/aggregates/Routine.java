package com.spottrack.platform.routine.domain.model.aggregates;
import com.spottrack.platform.routine.domain.model.commands.CreateRoutineCommand;
import com.spottrack.platform.routine.domain.model.entities.ExerciseBlock;
import com.spottrack.platform.routine.domain.model.events.ExerciseBlockAddedEvent;
import com.spottrack.platform.routine.domain.model.events.RoutineCreatedEvent;
import com.spottrack.platform.routine.domain.model.valueobjects.ExerciseName;
import com.spottrack.platform.routine.domain.model.valueobjects.ExerciseType;
import com.spottrack.platform.routine.domain.model.valueobjects.ProfileId;
import com.spottrack.platform.routine.domain.model.valueobjects.RoutineName;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Routine extends AbstractDomainAggregateRoot<Routine> {
    @Getter
    @Setter
    private Long id;
    @Getter
    private RoutineName routineName;
    private ProfileId profileId;
    private List<ExerciseBlock> exerciseBlocks;

    public Routine(Long id, RoutineName routineName, ProfileId profileId, List<ExerciseBlock> exerciseBlocks) {
        this.id = id;
        this.routineName = Objects.requireNonNull(routineName, "Routine name must not be null");
        this.profileId = Objects.requireNonNull(profileId, "Profile id must not be null");
        this.exerciseBlocks = exerciseBlocks != null ? exerciseBlocks : new ArrayList<>();
    }

    public Routine(CreateRoutineCommand command) {
        this(null, command.routineName(), command.profileId(), new ArrayList<>());
    }

    public void addExerciseBlock(ExerciseName exerciseName, ExerciseType exerciseType, int order) {
        var block = new ExerciseBlock(null, exerciseName, exerciseType, order);
        this.exerciseBlocks.add(block);
        registerDomainEvent(ExerciseBlockAddedEvent.from(this, block));
    }

    public void onCreated() {
        registerDomainEvent(RoutineCreatedEvent.from(this));
    }

    public ProfileId getProfileId() { return profileId; }
    public List<ExerciseBlock> getExerciseBlocks() { return exerciseBlocks; }
}