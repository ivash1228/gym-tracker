package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.WorkoutEntity;
import glushkova.kristina.gym_tracker.exceptions.ClientNotFoundException;
import glushkova.kristina.gym_tracker.mappers.WorkoutMapper;
import glushkova.kristina.gym_tracker.mappers.WorkoutMapperImpl;
import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.models.WorkoutModel;
import glushkova.kristina.gym_tracker.models.postModels.CreateWorkoutRequest;
import glushkova.kristina.gym_tracker.repositories.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkoutServiceTest {

    WorkoutRepository workoutRepository = Mockito.mock(WorkoutRepository.class);
    WorkoutMapper workoutMapper = new WorkoutMapperImpl();
    ClientService clientService = Mockito.mock(ClientService.class);
    WorkoutExerciseService workoutExerciseService = Mockito.mock(WorkoutExerciseService.class);
    WorkoutService workoutService = new WorkoutService(workoutRepository, clientService, workoutMapper, workoutExerciseService);

    UUID clientUuid = UUID.randomUUID();

    @Test
    void getAllWorkoutsByClientId_whenExistingClientIdProvided_ThenReturnClientAllWorkoutsList() {
        var clientWorkouts = List.of(new WorkoutEntity());

        when(clientService.getClientById(clientUuid)).thenReturn(new ClientModel(null, null, null, null, null, null));
        when(workoutRepository.findByClientId(clientUuid)).thenReturn(clientWorkouts);

        assertEquals(1, workoutService.getAllWorkoutsByClientId(clientUuid).size());
        verify(workoutRepository).findByClientId(clientUuid);
    }

    @Test
    void addWorkout_whenValidValuesProvided_thenReturnUuidOfCreatedWorkout() {
        var workoutEntity = new WorkoutEntity();
        workoutEntity.setId(UUID.randomUUID());
        var createWorkoutRequest = new CreateWorkoutRequest(LocalDate.now(), "upper body");

        when(clientService.getClientById(clientUuid)).thenReturn(new ClientModel(null, null, null, null, null, null));
        when(workoutRepository.save(any())).thenReturn(workoutEntity);

        assertEquals(workoutMapper.map(workoutEntity), workoutService.addWorkout(clientUuid, createWorkoutRequest.workoutDate(), createWorkoutRequest.workoutName()));
    }

    @Test
    void getAllWorkoutsByClientId_whenClientDoesNotExist_ThenThrowClientNotFoundException() {
        when(clientService.getClientById(clientUuid)).thenThrow(new ClientNotFoundException(clientUuid));

        var response = assertThrows(ClientNotFoundException.class, () -> workoutService.getAllWorkoutsByClientId(clientUuid));
        assertEquals("Client %s not found!".formatted(clientUuid), response.getMessage());
    }

    @Test
    void addWorkout_whenClientDoesNotExist_thenThrowClientNotFoundException() {
        var createWorkoutRequest = new CreateWorkoutRequest(LocalDate.now(), "upper body");

        when(clientService.getClientById(clientUuid)).thenThrow(new ClientNotFoundException(clientUuid));

        var response = assertThrows(ClientNotFoundException.class, () -> workoutService.addWorkout(clientUuid, createWorkoutRequest.workoutDate(), createWorkoutRequest.workoutName()));
        assertEquals("Client %s not found!".formatted(clientUuid), response.getMessage());
    }

    @Test
    void getWorkoutById_whenWorkoutExists_returnsWorkoutOptional() {
        var workoutId = UUID.randomUUID();
        var workoutModel = new WorkoutModel(workoutId, clientUuid, LocalDate.now(), "upper body");
        var workoutEntity = workoutMapper.map(workoutModel);

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workoutEntity));

        var result = workoutService.getWorkoutById(workoutId).get();
        assertEquals(workoutModel, result);
        verify(workoutRepository).findById(workoutId);
    }

    @Test
    void getWorkoutById_whenWorkoutNotFound_returnsEmptyOptional() {
        var workoutId = UUID.randomUUID();
        when(workoutRepository.findById(any())).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), workoutService.getWorkoutById(workoutId));
        verify(workoutRepository).findById(workoutId);
    }
}