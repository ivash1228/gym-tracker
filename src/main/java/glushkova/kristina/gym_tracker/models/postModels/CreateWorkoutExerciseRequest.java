package glushkova.kristina.gym_tracker.models.postModels;


import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateWorkoutExerciseRequest(
        @NotNull(message = "exerciseId cannot be null")
        UUID exerciseId
) {}
