package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.WorkoutEntity;
import glushkova.kristina.gym_tracker.exceptions.ClientNotFoundException;
import glushkova.kristina.gym_tracker.mappers.WorkoutMapper;
import glushkova.kristina.gym_tracker.mappers.WorkoutMapperImpl;
import glushkova.kristina.gym_tracker.models.ClientModel;
import glushkova.kristina.gym_tracker.models.postModels.CreateWorkoutRequest;
import glushkova.kristina.gym_tracker.repositories.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkoutServiceTest {

    WorkoutRepository workoutRepository = Mockito.mock(WorkoutRepository.class);
    WorkoutMapper workoutMapper = new WorkoutMapperImpl();
    ClientService clientService = Mockito.mock(ClientService.class);
    WorkoutService workoutService = new WorkoutService(workoutRepository, clientService, workoutMapper);

    @Test
    void getAllWorkoutsByClientId_whenExistingClientIdProvided_ThenReturnClientAllWorkoutsList() {
        var clientWorkouts = List.of(new WorkoutEntity());
        var uuid = UUID.randomUUID();

        when(clientService.getClientById(uuid)).thenReturn(new ClientModel(null, null, null, null, null, null));
        when(workoutRepository.findByClientId(uuid)).thenReturn(clientWorkouts);

        assertEquals(1, workoutService.getAllWorkoutsByClientId(uuid).size());
        verify(workoutRepository).findByClientId(uuid);
    }

    @Test
    void getAllWorkoutsByClientId_whenClientDoesNotExist_ThenThrowClientNotFoundException() {
        var uuid = UUID.randomUUID();

        when(clientService.getClientById(uuid)).thenReturn(null);

        var response = assertThrows(ClientNotFoundException.class, () -> workoutService.getAllWorkoutsByClientId(uuid));
        assertEquals("Client %s not found!".formatted(uuid), response.getMessage());
    }

    @Test
    void addWorkout_whenValidValuesProvided_thenReturnUuidOfCreatedWorkout() {
        var clientUuid = UUID.randomUUID();
        var workoutEntity = new WorkoutEntity();
        workoutEntity.setId(UUID.randomUUID());
        var createWorkoutRequest = new CreateWorkoutRequest(LocalDate.now(), "upper body");

        when(clientService.getClientById(clientUuid)).thenReturn(new ClientModel(null, null, null, null, null, null));
        when(workoutRepository.save(any())).thenReturn(workoutEntity);

        assertEquals(workoutEntity.getId(), workoutService.addWorkout(clientUuid, createWorkoutRequest));
    }

    @Test
    void addWorkout_whenClientDoesNotExist_thenThrowClientNotFoundException() {
        var clientUuid = UUID.randomUUID();
        var createWorkoutRequest = new CreateWorkoutRequest(LocalDate.now(), "upper body");

        when(clientService.getClientById(clientUuid)).thenReturn(null);

        var response = assertThrows(ClientNotFoundException.class, () -> workoutService.addWorkout(clientUuid, createWorkoutRequest));
        assertEquals("Client %s not found!".formatted(clientUuid), response.getMessage());
    }
}