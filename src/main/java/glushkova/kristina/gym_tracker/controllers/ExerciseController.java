package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.postModels.CreateExerciseRequest;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
import glushkova.kristina.gym_tracker.services.ExerciseService;
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
@RequestMapping("/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<UUID> createExercise(@Valid @RequestBody CreateExerciseRequest createExerciseRequest) {
        var createdExercise = exerciseService.createExercise(createExerciseRequest.name(), createExerciseRequest.type());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExercise.id());
    }

    @GetMapping(path = "/{exerciseId}")
    @ApiResponse(responseCode = "404", description = "Exercise not found")
    public ExerciseModel getExerciseById(@PathVariable UUID exerciseId) {
        return exerciseService.getExerciseById(exerciseId);
    }

    @GetMapping
    public List<ExerciseModel> getAllExercises() {
        return exerciseService.getAllExercises();
    }
}