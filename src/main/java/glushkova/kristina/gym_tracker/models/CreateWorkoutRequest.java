package glushkova.kristina.gym_tracker.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateWorkoutRequest(
        @NotNull(message = "workoutDate is required") LocalDate workoutDate,
        @NotEmpty(message = "workoutName is required") String workoutName
){}
