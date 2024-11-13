package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.WorkoutExerciseEntity;
import glushkova.kristina.gym_tracker.mappers.WorkoutExerciseMapper;
import glushkova.kristina.gym_tracker.models.WorkoutExerciseModel;
import glushkova.kristina.gym_tracker.repositories.WorkoutExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkoutExerciseService {
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutExerciseMapper workoutExerciseMapper;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository, WorkoutExerciseMapper workoutExerciseMapper) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutExerciseMapper = workoutExerciseMapper;
    }

    public UUID saveWorkoutExerciseRecord(UUID workoutUuid, UUID exerciseUuid, Integer order, Integer sets, Integer repCount, Integer weight) {
        var workoutExerciseRecord = new WorkoutExerciseModel(null, workoutUuid, exerciseUuid, order, sets,repCount, weight);
        return workoutExerciseRepository.save(workoutExerciseMapper.map(workoutExerciseRecord)).getId();
    }

    public List<UUID> getAllExercisesByWorkoutId(UUID workoutId) {
        return workoutExerciseRepository.findByWorkoutId(workoutId).stream()
                .map(WorkoutExerciseEntity::getExerciseId).toList();
    }
}
