package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.exceptions.ExerciseAlreadyExistsOnWorkoutException;
import glushkova.kristina.gym_tracker.models.WorkoutExerciseModel;
import glushkova.kristina.gym_tracker.models.postModels.CreateWorkoutExerciseRequest;
import glushkova.kristina.gym_tracker.services.WorkoutExerciseService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Client, workout, or exercise not found"),
            @ApiResponse(responseCode = "409", description = "Exercise already on this workout")
    })
    public ResponseEntity<UUID> addExerciseToWorkout(@PathVariable UUID clientId,
                                                     @PathVariable UUID workoutId,
                                                     @RequestBody CreateWorkoutExerciseRequest exerciseId) throws ExerciseAlreadyExistsOnWorkoutException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workoutExerciseService.saveWorkoutExerciseRecord(clientId, workoutId, exerciseId.exerciseId()).id());
    }

    //TODO two issues here - workoutExercise id is null inside set (mapper?) and we don't need sets in the response
    @GetMapping
    @ApiResponse(responseCode = "404", description = "Client or workout not found")
    public ResponseEntity<List<WorkoutExerciseModel>> getWorkoutExercises(@PathVariable UUID clientId, @PathVariable UUID workoutId) {
        var exerciseIds = workoutExerciseService.getAllExercisesByWorkoutId(workoutId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(exerciseIds);
    }
}
