package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.CreateExerciseDetails;
import glushkova.kristina.gym_tracker.models.WorkoutExerciseModel;
import glushkova.kristina.gym_tracker.services.ClientService;
import glushkova.kristina.gym_tracker.services.WorkoutExerciseService;
import glushkova.kristina.gym_tracker.services.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clients/{clientId}/workouts/{workoutId}/exercises")
public class WorkoutExerciseController {
    private final WorkoutExerciseService workoutExerciseService;

    @PostMapping
    public ResponseEntity<UUID> addExerciseToWorkout(@PathVariable UUID clientId, @PathVariable UUID workoutId,
                                                    @Valid @RequestBody CreateExerciseDetails exerciseDetails) {
            var recordUuid = workoutExerciseService.saveWorkoutExerciseRecord(clientId, workoutId, exerciseDetails.exerciseOrder(),
                    exerciseDetails.sets(), exerciseDetails.repsCount(), exerciseDetails.weights());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(recordUuid);
    }

}
