package glushkova.kristina.gym_tracker.models.responseModels;

import glushkova.kristina.gym_tracker.models.ExerciseModel;
import glushkova.kristina.gym_tracker.models.WorkoutModel;

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
