package glushkova.kristina.gym_tracker.models;

import jakarta.validation.constraints.NotNull;

public record CreateExerciseDetails (
        @NotNull(message = "exerciseOrder cannot be null")
        Integer exerciseOrder,
        @NotNull(message = "sets cannot be null")
        Integer sets,
        @NotNull(message = "weights cannot be null")
        Integer weights,
        @NotNull(message = "repsCount cannot be null")
        Integer repsCount
) {}
