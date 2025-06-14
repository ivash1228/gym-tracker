package glushkova.kristina.gym_tracker.models.postModels;

import glushkova.kristina.gym_tracker.models.ExerciseType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateExerciseRequest (
    @NotEmpty(message = "Please provide exercise name")
    String name,
    @NotNull(message = "type cannot be null, either SET or TIME") ExerciseType type
){}
