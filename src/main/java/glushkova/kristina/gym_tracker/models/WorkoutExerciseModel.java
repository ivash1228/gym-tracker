package glushkova.kristina.gym_tracker.models;

import java.util.UUID;

public record WorkoutExerciseModel(
        UUID id,
        UUID workoutId,
        UUID exerciseId
) {}