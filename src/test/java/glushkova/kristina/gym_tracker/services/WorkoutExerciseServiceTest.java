package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.WorkoutExerciseEntity;
import glushkova.kristina.gym_tracker.repositories.WorkoutExerciseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WorkoutExerciseServiceTest {
    private final WorkoutExerciseRepository workoutExerciseRepository = Mockito.mock(WorkoutExerciseRepository.class);
    private final WorkoutExerciseService workoutExerciseService = new WorkoutExerciseService(workoutExerciseRepository);

    @Test
    public void addExerciseToWorkout_WhenUuidsProvided_ShouldSaveRecordToDb() {
        var workoutExerciseUuid = UUID.randomUUID();

        var entity = new WorkoutExerciseEntity(workoutExerciseUuid, UUID.randomUUID(), UUID.randomUUID());

        when(workoutExerciseRepository.save(any())).thenReturn(entity);

        var response = workoutExerciseService.saveWorkoutExerciseRecord(UUID.randomUUID(), UUID.randomUUID());
        //add assetrts
    }
}
