package glushkova.kristina.gym_tracker.models;

import java.util.UUID;

public record WorkoutExerciseModel(
        UUID id,
        UUID workoutId,
        UUID exerciseId,
        Integer order,
        Integer sets,
        Integer weights,
        Integer repsCount
) {}