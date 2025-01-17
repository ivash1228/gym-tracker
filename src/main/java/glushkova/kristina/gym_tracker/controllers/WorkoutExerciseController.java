package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.exceptions.ExerciseAlreadyExistsOnWorkoutException;
import glushkova.kristina.gym_tracker.models.WorkoutExerciseModel;
import glushkova.kristina.gym_tracker.models.postModels.CreateWorkoutExerciseRequest;
import glushkova.kristina.gym_tracker.services.WorkoutExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clients/{clientId}/workouts/{workoutId}/exercises")
public class WorkoutExerciseController {
    private final WorkoutExerciseService workoutExerciseService;

    @PostMapping
    public ResponseEntity<UUID> addExerciseToWorkout(@PathVariable UUID clientId,
                                                     @PathVariable UUID workoutId,
                                                     @RequestBody CreateWorkoutExerciseRequest exerciseId) throws ExerciseAlreadyExistsOnWorkoutException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workoutExerciseService.saveWorkoutExerciseRecord(clientId, workoutId, exerciseId.exerciseId()).id());
    }

    //TODO two issues here - workoutExercise id is null inside set (mapper?) and we dont need sets in the response here
    @GetMapping
    public ResponseEntity<List<WorkoutExerciseModel>> getWorkoutExercises(@PathVariable UUID clientId, @PathVariable UUID workoutId) {
        var exerciseIds = workoutExerciseService.getAllExercisesByWorkoutId(workoutId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(exerciseIds);
    }
}
