package glushkova.kristina.gym_tracker.exceptions;

import java.util.UUID;

public class WorkoutNotFoundException extends RuntimeException {
    public WorkoutNotFoundException(UUID workoutId) {
        super("Workout not found");
    }
}
