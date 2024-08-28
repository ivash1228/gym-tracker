package glushkova.kristina.gym_tracker.models;

import java.time.LocalDate;
import java.util.List;

public record WorkoutDetailsResponse(
        String name,
        LocalDate workoutDate,
        List<ExerciseModel> exerciseList
){
    public static WorkoutDetailsResponse from(WorkoutModel workoutModel, List<ExerciseModel> exerciseList) {
        return new WorkoutDetailsResponse(workoutModel.workoutName(), workoutModel.workoutDate(), exerciseList);
    }
}
