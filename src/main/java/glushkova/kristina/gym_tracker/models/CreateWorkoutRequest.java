package glushkova.kristina.gym_tracker.models;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateWorkout (
        @NotNull(message = "workoutDate is required") LocalDate workoutDate,
        @NotNull(message = "workoutName is required") String workoutName
){}
