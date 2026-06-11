package com.spottrack.platform.routine.domain.model.entities;
import com.spottrack.platform.routine.domain.model.valueobjects.ExerciseName;
import com.spottrack.platform.routine.domain.model.valueobjects.ExerciseType;
import lombok.Getter;

@Getter

public class ExerciseBlock {
    private Long id;
    private ExerciseName exerciseName;
    private ExerciseType exerciseType;
    private int order;

    public ExerciseBlock(Long id, ExerciseName exerciseName, ExerciseType exerciseType, int order) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.exerciseType = exerciseType;
        this.order = order;
    }
}