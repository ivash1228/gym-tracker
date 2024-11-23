package glushkova.kristina.gym_tracker.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record WorkoutModel (
        UUID id,
        UUID clientId,
        LocalDate workoutDate,
        String workoutName
) {}
