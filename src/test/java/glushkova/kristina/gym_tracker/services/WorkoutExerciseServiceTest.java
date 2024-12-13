package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.WorkoutExerciseEntity;
import glushkova.kristina.gym_tracker.mappers.WorkoutExerciseMapper;
import glushkova.kristina.gym_tracker.mappers.WorkoutExerciseMapperImpl;
import glushkova.kristina.gym_tracker.models.WorkoutExerciseModel;
import glushkova.kristina.gym_tracker.repositories.WorkoutExerciseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WorkoutExerciseServiceTest {
    private final WorkoutExerciseRepository workoutExerciseRepository = Mockito.mock(WorkoutExerciseRepository.class);
    private final WorkoutExerciseMapper workoutExerciseMapper = new WorkoutExerciseMapperImpl();
    private final WorkoutExerciseService workoutExerciseService = new WorkoutExerciseService(workoutExerciseRepository, workoutExerciseMapper);

//    @Test
//    public void saveWorkoutExerciseRecord_WhenValidUuidsProvided_ShouldSaveRecordToDb() {
//        var workoutExerciseUuid = UUID.randomUUID();
//        var requestEntity = new WorkoutExerciseEntity();
//        var responseEntity = new WorkoutExerciseEntity();
//        responseEntity.setId(workoutExerciseUuid);
//
//        when(workoutExerciseRepository.save(any())).thenReturn(responseEntity);
//
//        var response = workoutExerciseService.saveWorkoutExerciseRecord(null, null);
//        assertEquals(workoutExerciseUuid, response);
//        verify(workoutExerciseRepository).save(requestEntity);
//    }

    @Test
    void getAllExercisesByWorkoutId_WhenValidWorkoutIdProvided_ThenShouldReturnAllExercisesById() {
        var exerciseUuid = UUID.randomUUID();
        var entity = new WorkoutExerciseEntity();
        entity.setExerciseId(exerciseUuid);
        var listOfExercises = List.of(entity);
        var uuid = UUID.randomUUID();

        when(workoutExerciseRepository.findByWorkoutId(any())).thenReturn(listOfExercises);

        var response = workoutExerciseService.getAllExercisesByWorkoutId(uuid);
        assertEquals(response.size(), 1);
        assertEquals(response.getFirst(), exerciseUuid);
        verify(workoutExerciseRepository).findByWorkoutId(uuid);
    }
}