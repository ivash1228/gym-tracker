package glushkova.kristina.gym_tracker.exceptions;

import java.util.UUID;

public class WorkoutExerciseNotFoundException extends RuntimeException{
    public WorkoutExerciseNotFoundException(UUID id) {
        super("No record with " + id + " found!");
    }
}
