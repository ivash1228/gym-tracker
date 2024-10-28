package glushkova.kristina.gym_tracker.models;

import jakarta.validation.constraints.NotEmpty;

public record CreateExerciseRequest (
    @NotEmpty(message = "Please provide exercise name")
    String name,
    @NotEmpty ExerciseType type
){}
