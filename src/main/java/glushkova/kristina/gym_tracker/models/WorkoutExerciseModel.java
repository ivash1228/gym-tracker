package glushkova.kristina.gym_tracker.models;

import java.util.List;
import java.util.UUID;

public record WorkoutExerciseModel(
        UUID id,
        UUID workoutId,
        UUID exerciseId,
        Integer exerciseOrder,
        //optional field - not all exc has sets
        List<SetModel> exercise_sets
) {}