package glushkova.kristina.gym_tracker.models;

import java.util.UUID;

public record SetModel (
    UUID id,
    UUID workoutExerciseId,
    Integer weights,
    Integer reps,
    Integer setOrder
) {}
