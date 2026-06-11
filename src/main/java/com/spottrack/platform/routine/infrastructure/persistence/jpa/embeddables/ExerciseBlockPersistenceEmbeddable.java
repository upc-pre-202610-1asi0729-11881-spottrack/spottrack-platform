package com.spottrack.platform.routine.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ExerciseBlockPersistenceEmbeddable {

    @Column(name = "id")
    private Long id;

    @Column(name = "exercise_name")
    private String exerciseName;

    @Column(name = "exercise_type")
    private String exerciseType;

    @Column(name = "exercise_order")
    private int order;

    public ExerciseBlockPersistenceEmbeddable() {
    }

    public ExerciseBlockPersistenceEmbeddable(Long id, String exerciseName, String exerciseType, int order) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.exerciseType = exerciseType;
        this.order = order;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public String getExerciseType() { return exerciseType; }
    public void setExerciseType(String exerciseType) { this.exerciseType = exerciseType; }

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
}
