package glushkova.kristina.gym_tracker.models.responseModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDetailsResponse {
    private Map<String, ExerciseWithSets> orderedExercisesWithSets;
}