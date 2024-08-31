package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.WorkoutExerciseEntity;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
import glushkova.kristina.gym_tracker.repositories.WorkoutExerciseRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkoutExerciseService {
    private final WorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public UUID saveWorkoutExerciseRecord(UUID workoutUuid, UUID exerciseUuid) {
        return workoutExerciseRepository.save(new WorkoutExerciseEntity(null, workoutUuid, exerciseUuid)).getId();
    }

    public List<UUID> getAllExercisesByWorkoutId(@Valid UUID workoutId) {
        return workoutExerciseRepository.findAllById(List.of(workoutId)).stream()
                .map(WorkoutExerciseEntity::getId).toList();
    }
}
