package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.exceptions.ClientNotFoundException;
import glushkova.kristina.gym_tracker.exceptions.WorkoutNotFoundException;
import glushkova.kristina.gym_tracker.mappers.WorkoutMapper;
import glushkova.kristina.gym_tracker.models.CreateWorkoutRequest;
import glushkova.kristina.gym_tracker.models.WorkoutModel;
import glushkova.kristina.gym_tracker.repositories.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final ClientService clientService;
    private final WorkoutMapper workoutMapper;

    public WorkoutService(WorkoutRepository workoutRepository, ClientService clientService, WorkoutMapper workoutMapper) {
        this.workoutRepository = workoutRepository;
        this.clientService = clientService;
        this.workoutMapper = workoutMapper;
    }

    public List<WorkoutModel> getAllWorkoutsByClientId(UUID clientId) {
        if (clientService.getClientById(clientId) == null) {
            throw new ClientNotFoundException(clientId);
        }
        return workoutRepository.findByClientId(clientId).stream()
                .map(workoutMapper::map)
                .toList();
    }

    public UUID addWorkout(UUID clientId, CreateWorkoutRequest createWorkoutRequest) {
        if (clientService.getClientById(clientId) == null) throw new ClientNotFoundException(clientId);
        var workout = new WorkoutModel(null, clientId, createWorkoutRequest.workoutDate(), createWorkoutRequest.workoutName());
        return workoutRepository.save(workoutMapper.map(workout)).getId();
    }

    public Optional<WorkoutModel> getWorkoutById(UUID workoutId) {
        var workout = workoutRepository.findById(workoutId);
        if(workout.isEmpty()) return Optional.empty();
        else return Optional.of(workoutMapper.map(workout.get()));
    }
    //can I use mapper to map clientWorkoutRequest straight to entity?
}
