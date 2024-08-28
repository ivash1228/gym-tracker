package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.mappers.WorkoutMapper;
import glushkova.kristina.gym_tracker.models.CreateWorkout;
import glushkova.kristina.gym_tracker.models.WorkoutDetailsResponse;
import glushkova.kristina.gym_tracker.models.WorkoutModel;
import glushkova.kristina.gym_tracker.services.ExerciseService;
import glushkova.kristina.gym_tracker.services.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients/{clientId}/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;

    public WorkoutController(WorkoutService workoutService, ExerciseService exerciseService, WorkoutMapper workoutMapper) {
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<UUID> addWorkout(@PathVariable UUID clientId, @Valid @RequestBody CreateWorkout createWorkout) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.addWorkout(clientId, createWorkout));
    }

    @GetMapping
    public ResponseEntity<List<WorkoutModel>> getAllWorkoutsByClientId(@PathVariable UUID clientId) {
        return ResponseEntity.status(HttpStatus.OK)
                        .body(workoutService.getAllWorkoutsByClientId(clientId));
    }

    @GetMapping(path = "/{workoutId}")
    public ResponseEntity<WorkoutDetailsResponse> getWorkoutById(@PathVariable UUID clientId, @PathVariable UUID workoutId) {
        //should we check client?
        var workout = workoutService.getWorkoutById(clientId, workoutId);
        //check if this client has this workout
        var exercises = exerciseService.getExercisesByWorkoutId(workoutId);
        return ResponseEntity.status(HttpStatus.OK).body(WorkoutDetailsResponse.from(workout, exercises));
    }

    @PutMapping(path = "/{workoutId}/exercise")
    public ResponseEntity<WorkoutDetailsResponse> modifyWorkout(@PathVariable UUID workoutId) {
        return null;
    }
}
