package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.exceptions.WorkoutExerciseNotFoundException;
import glushkova.kristina.gym_tracker.exceptions.WorkoutNotFoundException;
import glushkova.kristina.gym_tracker.mappers.SetMapper;
import glushkova.kristina.gym_tracker.models.SetModel;
import glushkova.kristina.gym_tracker.repositories.SetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SetService {
    private final SetMapper setMapper;
    private final SetRepository setRepository;
    private final WorkoutExerciseService workoutExerciseService;
    private final ClientService clientService;
    private final WorkoutService workoutService;

    public SetService(SetMapper setMapper, SetRepository setRepository, WorkoutExerciseService workoutExerciseService, ClientService clientService, WorkoutService workoutService) {
        this.setMapper = setMapper;
        this.setRepository = setRepository;
        this.workoutExerciseService = workoutExerciseService;
        this.clientService = clientService;
        this.workoutService = workoutService;
    }

    public SetModel saveSetToWorkout(UUID clientId, UUID workoutId, UUID workoutExerciseId, Integer reps, Integer weights) {
        clientService.getClientById(clientId);
        workoutService.getWorkoutById(workoutId).orElseThrow(() -> new WorkoutNotFoundException(workoutId));
        workoutExerciseService.findById(workoutExerciseId).orElseThrow(() -> new WorkoutExerciseNotFoundException(workoutExerciseId));
        var order = getAllSetsForExercise(workoutExerciseId).size() + 1;
        var model = new SetModel(null, workoutExerciseId, reps, weights, order);
        var entity = setMapper.map(model);
        var response = setRepository.save(entity);
        return setMapper.map(response);
    }

    public List<SetModel> getAllSetsForExercise(UUID workoutExerciseId) {
        return setRepository.findByWorkoutExerciseEntity_Id(workoutExerciseId).stream()
                .map(setMapper::map).toList();
    }
}
