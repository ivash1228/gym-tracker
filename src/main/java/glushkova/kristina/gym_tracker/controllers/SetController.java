package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.postModels.CreateSetDetails;
import glushkova.kristina.gym_tracker.services.SetService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clients/{clientId}/workouts/{workoutId}/exercises/{workoutExerciseId}/sets")
public class SetController {
    private final SetService setService;

    public SetController(SetService setService) {
        this.setService = setService;
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<UUID> addSetToWorkout(@PathVariable UUID clientId,
                                                @PathVariable UUID workoutId,
                                                @PathVariable UUID workoutExerciseId,
                                                @Valid @RequestBody CreateSetDetails setDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(setService.saveSetToWorkout(clientId, workoutId, workoutExerciseId, setDetails.reps(), setDetails.weights()).id());
    }
}
