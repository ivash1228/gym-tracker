package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.WorkoutExerciseEntity;
import glushkova.kristina.gym_tracker.exceptions.ExerciseAlreadyExistsOnWorkoutException;
import glushkova.kristina.gym_tracker.mappers.WorkoutExerciseMapperImpl;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
import glushkova.kristina.gym_tracker.repositories.WorkoutExerciseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.UUID;

import static glushkova.kristina.gym_tracker.models.ExerciseType.SET;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WorkoutExerciseServiceTest {
    private final WorkoutExerciseRepository workoutExerciseRepository = Mockito.mock(WorkoutExerciseRepository.class);
    private final WorkoutExerciseMapperImpl workoutExerciseMapper = new WorkoutExerciseMapperImpl();
    private final ExerciseService exerciseService = Mockito.mock(ExerciseService.class);
    private final WorkoutExerciseService workoutExerciseService = new WorkoutExerciseService(workoutExerciseRepository,
            workoutExerciseMapper, exerciseService);

    @Test
    public void saveWorkoutExerciseRecord_WhenValidUuidsProvided_ShouldSaveRecordToDb() throws ExerciseAlreadyExistsOnWorkoutException {
        var workoutExerciseUuid = UUID.randomUUID();
        var responseEntity = new WorkoutExerciseEntity();
        responseEntity.setId(workoutExerciseUuid);

        when(exerciseService.getExerciseById(any())).thenReturn(new ExerciseModel(UUID.randomUUID(), "name", SET));
        when(workoutExerciseRepository.save(any())).thenReturn(responseEntity);

        var response = workoutExerciseService.saveWorkoutExerciseRecord(UUID.randomUUID(), UUID.randomUUID(), workoutExerciseUuid);
        assertEquals(workoutExerciseUuid, response.id());
        verify(workoutExerciseRepository).save(any());
    }

    @Test
    public void saveWorkoutExerciseRecord_whenSameExerciseSavedTwice_ShouldThrowExerciseAlreadyExistsOnWorkoutException() {

        UUID clientId = UUID.randomUUID();
        UUID workoutId = UUID.randomUUID();
        UUID exerciseId = UUID.randomUUID();

        var workoutExerciseRecord = new WorkoutExerciseEntity(null, workoutId, exerciseId, 1, null);
        when(workoutExerciseRepository.save(workoutExerciseRecord)).thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> workoutExerciseService.saveWorkoutExerciseRecord(clientId, workoutId, exerciseId)).isInstanceOf(ExerciseAlreadyExistsOnWorkoutException.class);
    }

    @Test
    void getAllExercisesByWorkoutId_WhenValidWorkoutIdProvided_ThenShouldReturnAllExercises() {
        var workoutUuid = UUID.randomUUID();
        var exerciseUuid = UUID.randomUUID();
        var entity = new WorkoutExerciseEntity(UUID.randomUUID(), workoutUuid, exerciseUuid, 1, null);
        var listOfExercises = List.of(entity);

        when(workoutExerciseRepository.findByWorkoutId(any())).thenReturn(listOfExercises);

        var response = workoutExerciseService.getAllExercisesByWorkoutId(workoutUuid);
        assertEquals(1, response.size());
        assertEquals(exerciseUuid, response.getFirst().exerciseId());
        verify(workoutExerciseRepository).findByWorkoutId(workoutUuid);
    }
}