package glushkova.kristina.gym_tracker.models.postModels;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateWorkoutRequest(
        @NotNull(message = "workoutDate is required") LocalDate workoutDate,
        @NotEmpty(message = "workoutName is required")
        @Size(max = 30, message = "Workout name must be at most 30 characters")
        String workoutName
){}
