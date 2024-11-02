package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.exceptions.WorkoutNotFoundException;
import glushkova.kristina.gym_tracker.models.*;
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

    public WorkoutController(WorkoutService workoutService, ExerciseService exerciseService, WorkoutExerciseService workoutExerciseService) {
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
    public ResponseEntity<WorkoutModel> getWorkoutById(@PathVariable UUID clientId, @PathVariable UUID workoutId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(workoutService.getWorkoutById(workoutId).orElseThrow(() -> new WorkoutNotFoundException(workoutId)));
    }

//    @PostMapping(path = "/{workoutId}")
//    public ResponseEntity<UUID> addExerciseToWorkoutRecord(@PathVariable UUID clientId, @PathVariable UUID workoutId, @RequestBody ExerciseUuid id) {
//        //add check for client id in service
//        return ResponseEntity.status(HttpStatus.CREATED).body(workoutExerciseService.saveWorkoutExerciseRecord(workoutId, id.id()));
//    }

//    @GetMapping(path = "/{workoutId}")
//    public ResponseEntity<List<ExerciseModel>> getAllExercisesForWorkout(@PathVariable UUID workoutId) {
//        var exerciseIds = workoutExerciseService.getAllExercisesByWorkoutId(workoutId);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(exerciseService.getExercisesByIds(exerciseIds));
//    }
}
