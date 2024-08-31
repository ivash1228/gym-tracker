package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.mappers.WorkoutMapper;
import glushkova.kristina.gym_tracker.models.CreateWorkoutRequest;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
import glushkova.kristina.gym_tracker.models.WorkoutDetailsResponse;
import glushkova.kristina.gym_tracker.models.WorkoutModel;
import glushkova.kristina.gym_tracker.services.ExerciseService;
import glushkova.kristina.gym_tracker.services.WorkoutExerciseService;
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
    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutController(WorkoutService workoutService, ExerciseService exerciseService, WorkoutMapper workoutMapper, WorkoutExerciseService workoutExerciseService) {
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
        this.workoutExerciseService = workoutExerciseService;
    }

    @PostMapping
    public ResponseEntity<UUID> addWorkout(@PathVariable UUID clientId, @Valid @RequestBody CreateWorkoutRequest createWorkoutRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.addWorkout(clientId, createWorkoutRequest));
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
        var exerciseIds = workoutExerciseService.getAllExercisesByWorkoutId(workoutId);

        return ResponseEntity.status(HttpStatus.OK).body(WorkoutDetailsResponse.from(workout, exerciseService.getExercisesByIds(exerciseIds)));
    }

    @PostMapping(path = "/{workoutId}")
    public ResponseEntity<UUID> addExerciseToWorkout(@PathVariable UUID workoutId, @Valid @RequestBody UUID exerciseId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutExerciseService.saveWorkoutExerciseRecord(workoutId, exerciseId));
    }

    @GetMapping
    public ResponseEntity<List<ExerciseModel>> getAllByWorkoutId(@Valid @RequestBody UUID workoutId) {
        var exerciseIds = workoutExerciseService.getAllExercisesByWorkoutId(workoutId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(exerciseService.getExercisesByIds(exerciseIds));
    }
}
