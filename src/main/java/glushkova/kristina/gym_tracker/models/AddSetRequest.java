package glushkova.kristina.gym_tracker.models;

import java.util.UUID;

public record AddSetRequest (
        UUID workoutId,
        UUID exerciseId,
        Integer reps,
        Integer weight,
        String note
){
}
