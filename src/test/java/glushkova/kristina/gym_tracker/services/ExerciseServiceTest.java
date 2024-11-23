package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.ExerciseEntity;
import glushkova.kristina.gym_tracker.exceptions.ExerciseNotFoundException;
import glushkova.kristina.gym_tracker.mappers.ExerciseMapper;
import glushkova.kristina.gym_tracker.mappers.ExerciseMapperImpl;
import glushkova.kristina.gym_tracker.repositories.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExerciseServiceTest {

    ExerciseRepository exerciseRepository = Mockito.mock(ExerciseRepository.class);
    ExerciseMapper exerciseMapper = new ExerciseMapperImpl();
    ExerciseService exerciseService = new ExerciseService(exerciseRepository, exerciseMapper);

    @Test
    void getExerciseById_whenValidUuidProvided_thenReturnsExercise() {
        var exerciseId = UUID.randomUUID();
        var exercise = new ExerciseEntity();
        exercise.setId(exerciseId);

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));

        assertEquals(exerciseId, exerciseService.getExerciseById(exerciseId).id());
        verify(exerciseRepository).findById(exerciseId);
    }

    @Test
    void getExerciseById_whenNonExistingUuidProvided_thenThrowsException() {
        var exerciseId = UUID.randomUUID();

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> exerciseService.getExerciseById(exerciseId) );
        verify(exerciseRepository).findById(exerciseId);
    }


    @Test
    void getAllPossibleExercises_whenCalled_ReturnsAllExercises() {
        var exerciseList = List.of(new ExerciseEntity(), new ExerciseEntity(), new ExerciseEntity());

        when(exerciseRepository.findAll()).thenReturn(exerciseList);

        assertEquals(3, exerciseService.getAllExercises().size());
        verify(exerciseRepository).findAll();
    }
}