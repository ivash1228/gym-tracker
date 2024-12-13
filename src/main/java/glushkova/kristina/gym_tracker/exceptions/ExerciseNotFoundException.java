package glushkova.kristina.gym_tracker.exceptions;

import java.util.UUID;

public class ExerciseNotFoundException extends RuntimeException {
    public ExerciseNotFoundException(UUID exerciseId) {
        super("Exercise with id %s not found".formatted(exerciseId));
    }
}
