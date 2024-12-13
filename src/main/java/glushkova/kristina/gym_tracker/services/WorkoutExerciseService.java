package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.WorkoutExerciseEntity;
import glushkova.kristina.gym_tracker.mappers.WorkoutExerciseMapper;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
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

    public List<WorkoutExerciseModel> getAllExercisesByWorkoutId(UUID workoutId) {
        return workoutExerciseRepository.findByWorkoutId(workoutId).stream()
                .map(workoutExerciseMapper::map).toList();
               // .map(WorkoutExerciseEntity::getExerciseId).toList();
    }

//    public List<WorkoutExerciseModel> getAllExercisesForWorkoutByIds(List<UUID> exerciseIds) {
//        return workoutExerciseRepository.findAllById(exerciseIds).stream()
//                .map(workoutExerciseMapper::map).toList();
//    }
}
