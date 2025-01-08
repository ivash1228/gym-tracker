package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.WorkoutModel;
import glushkova.kristina.gym_tracker.models.postModels.CreateWorkoutRequest;
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

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<UUID> addWorkout(@PathVariable UUID clientId, @Valid @RequestBody CreateWorkoutRequest createWorkoutRequest) {
        var createdWorkoutId = workoutService.addWorkout(clientId, createWorkoutRequest.workoutDate(), createWorkoutRequest.workoutName()).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkoutId);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutModel>> getAllWorkoutsForClient(@PathVariable UUID clientId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(workoutService.getAllWorkoutsByClientId(clientId));
    }

    @GetMapping(path = "/{workoutId}")
    public ResponseEntity<?> getFullWorkout(@PathVariable UUID clientId, @PathVariable UUID workoutId) {
        var fullWorkout = workoutService.getFullWorkoutById(clientId, workoutId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(fullWorkout);
    }
}
