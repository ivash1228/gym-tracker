package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.WorkoutEntity;
import glushkova.kristina.gym_tracker.mappers.WorkoutMapper;
import glushkova.kristina.gym_tracker.models.WorkoutModel;
import glushkova.kristina.gym_tracker.repositories.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final ClientService clientService;
    private final WorkoutMapper workoutMapper;
    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutService(WorkoutRepository workoutRepository, ClientService clientService, WorkoutMapper workoutMapper, WorkoutExerciseService workoutExerciseService) {
        this.workoutRepository = workoutRepository;
        this.clientService = clientService;
        this.workoutMapper = workoutMapper;
        this.workoutExerciseService = workoutExerciseService;
    }

    public WorkoutModel addWorkout(UUID clientId, LocalDate workoutDate, String workoutName) {
        WorkoutModel createWorkout = new WorkoutModel(null, clientId, workoutDate, workoutName);
        clientService.getClientById(clientId);
        WorkoutEntity createdWorkout = workoutRepository.save(workoutMapper.map(createWorkout));
        return workoutMapper.map(createdWorkout);
    }

    public List<WorkoutModel> getAllWorkoutsByClientId(UUID clientId) {
        clientService.getClientById(clientId);
        return workoutRepository.findByClientId(clientId).stream()
                .map(workoutMapper::map)
                .toList();
    }

    public Object getFullWorkoutById(UUID clientId, UUID workoutId) {
        clientService.getClientById(clientId);
        getWorkoutById(workoutId);
        return workoutExerciseService.getFullWorkout(clientId, workoutId);
    }

    public Optional<WorkoutModel> getWorkoutById(UUID workoutId) {
        var workout = workoutRepository.findById(workoutId);
        if(workout.isEmpty()) return Optional.empty();
        else return Optional.of(workoutMapper.map(workout.get()));
    }
}
