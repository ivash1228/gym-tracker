package glushkova.kristina.gym_tracker.models.responseModels;

import glushkova.kristina.gym_tracker.models.SetModel;

import java.util.List;
import java.util.UUID;

public record ExerciseWithSets (
        UUID workoutExerciseId,
        List<SetModel> sets
){}
