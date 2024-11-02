package glushkova.kristina.gym_tracker.controllers;

import glushkova.kristina.gym_tracker.models.CreateExerciseRequest;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
import glushkova.kristina.gym_tracker.services.ExerciseService;
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
    public ResponseEntity<UUID> createExercise(@Valid @RequestBody CreateExerciseRequest createExerciseRequest) {
        var uuid = exerciseService.createExercise(createExerciseRequest.name(), createExerciseRequest.type());
        return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
    }

    @GetMapping
    public List<ExerciseModel> getAllPossibleExercises() {
        return exerciseService.getAllPossibleExercises();
    }
}