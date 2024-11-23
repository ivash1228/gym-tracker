package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.postModels.CreateExerciseDetails;
import glushkova.kristina.gym_tracker.services.WorkoutExerciseService;
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

    @PostMapping(path = "/{exerciseId}")
    public ResponseEntity<UUID> addExerciseToWorkout(@PathVariable UUID workoutId,
                                                     @PathVariable UUID exerciseId,
                                                     @Valid @RequestBody CreateExerciseDetails exerciseDetails) {
            var recordUuid = workoutExerciseService.saveWorkoutExerciseRecord(workoutId, exerciseId, exerciseDetails.exerciseOrder(),
                    exerciseDetails.sets(), exerciseDetails.repsCount(), exerciseDetails.weights());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(recordUuid);
    }
}
