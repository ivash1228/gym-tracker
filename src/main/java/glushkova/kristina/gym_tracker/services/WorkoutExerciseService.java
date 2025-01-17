package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.exceptions.ExerciseAlreadyExistsOnWorkoutException;
import glushkova.kristina.gym_tracker.exceptions.ExerciseNotFoundException;
import glushkova.kristina.gym_tracker.mappers.WorkoutExerciseMapperImpl;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
import glushkova.kristina.gym_tracker.models.WorkoutExerciseModel;
import glushkova.kristina.gym_tracker.models.responseModels.ExerciseWithSets;
import glushkova.kristina.gym_tracker.models.responseModels.WorkoutDetailsResponse;
import glushkova.kristina.gym_tracker.repositories.WorkoutExerciseRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkoutExerciseService {
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutExerciseMapperImpl workoutExerciseMapper;
    private final ExerciseService exerciseService;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository, WorkoutExerciseMapperImpl workoutExerciseMapper, ExerciseService exerciseService) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutExerciseMapper = workoutExerciseMapper;
        this.exerciseService = exerciseService;
    }

    public WorkoutExerciseModel saveWorkoutExerciseRecord(UUID clientId, UUID workoutUuid, UUID exerciseUuid) throws ExerciseAlreadyExistsOnWorkoutException {
        //replace with calling exercise service and add client and workout uuid validations
        //validate(exerciseUuid);
        //TODO join tables to validate client, workout, ...
        exerciseService.getExerciseById(exerciseUuid);
        var order = getAllExercisesByWorkoutId(workoutUuid).size() + 1;
        var workoutExerciseRecord = new WorkoutExerciseModel(null, workoutUuid, exerciseUuid, order, null);

        try {
            var savedRecord = workoutExerciseRepository.save(workoutExerciseMapper.map(workoutExerciseRecord));
            return workoutExerciseMapper.map(savedRecord);
        } catch (DataIntegrityViolationException e) {
            throw new ExerciseAlreadyExistsOnWorkoutException("Exercise already exists");
        }
    }

    public List<WorkoutExerciseModel> getAllExercisesByWorkoutId(UUID workoutId) {
        return workoutExerciseRepository.findByWorkoutId(workoutId).stream()
                .map(workoutExerciseMapper::map).toList();
    }

    public WorkoutDetailsResponse getFullWorkout(UUID clientId, UUID workoutId) {
        //add client and workout uuid validations
        var response = workoutExerciseRepository.findByWorkoutId(workoutId)
                .stream().map(workoutExerciseMapper::map)
                .toList();

        Map<UUID, ExerciseWithSets> workoutDetails =  response.stream()
                .collect(Collectors.toMap(
                        //this is a key of new map
                        WorkoutExerciseModel::exerciseId,
                        //this is the value, that should be an object exerciseWithSet
                        item -> new ExerciseWithSets(item.id(), item.exercise_sets())));
        //get exercise ids and names
        var exercises = exerciseService.getAllExercises().stream().collect(Collectors.toMap(ExerciseModel::id, ExerciseModel::name));

        var result = workoutDetails.entrySet().stream()
                .collect(Collectors.toMap(entry -> exercises.get(entry.getKey()),
                        Map.Entry::getValue));

        return new WorkoutDetailsResponse(result);
    }

    private void validate(UUID... exerciseId) {
        for (UUID id : exerciseId) {
            if (exerciseService.getExerciseById(id) == null) throw new ExerciseNotFoundException(id);
        }
    }

    public Optional<WorkoutExerciseModel> findById(UUID workoutExerciseId) {
        return workoutExerciseRepository.findById(workoutExerciseId).map(workoutExerciseMapper::map);
    }
}
