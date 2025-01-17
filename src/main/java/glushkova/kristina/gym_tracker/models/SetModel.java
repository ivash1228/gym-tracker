package glushkova.kristina.gym_tracker.models;

import java.util.UUID;

public record SetModel (
    UUID id,
    UUID workoutExerciseId,
    Integer weights,
    Integer reps,
    Integer setOrder
) {
    public static SetModel from (SetModel setModel, UUID workoutExerciseId) {
        return new SetModel(
                setModel.id, workoutExerciseId, setModel.weights, setModel.reps, setModel.setOrder
        );
    }
}
