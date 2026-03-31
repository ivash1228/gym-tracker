package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.WorkoutModel;
import glushkova.kristina.gym_tracker.models.postModels.CreateWorkoutRequest;
import glushkova.kristina.gym_tracker.services.WorkoutService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<UUID> addWorkout(@PathVariable UUID clientId, @Valid @RequestBody CreateWorkoutRequest createWorkoutRequest) {
        var createdWorkoutId = workoutService.addWorkout(clientId, createWorkoutRequest.workoutDate(), createWorkoutRequest.workoutName()).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkoutId);
    }

    @GetMapping
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<List<WorkoutModel>> getAllWorkoutsForClient(@PathVariable UUID clientId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(workoutService.getAllWorkoutsByClientId(clientId));
    }

    @GetMapping(path = "/{workoutId}")
    @ApiResponse(responseCode = "404", description = "Client or workout not found")
    public ResponseEntity<?> getFullWorkout(@PathVariable UUID clientId, @PathVariable UUID workoutId) {
        var fullWorkout = workoutService.getFullWorkoutById(clientId, workoutId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(fullWorkout);
    }
}
