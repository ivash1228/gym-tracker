package glushkova.kristina.gym_tracker.services;

import glushkova.kristina.gym_tracker.entities.ExerciseEntity;
import glushkova.kristina.gym_tracker.mappers.ExerciseMapper;
import glushkova.kristina.gym_tracker.mappers.ExerciseMapperImpl;
import glushkova.kristina.gym_tracker.repositories.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExerciseServiceTest {

    ExerciseRepository exerciseRepository = Mockito.mock(ExerciseRepository.class);
    ExerciseMapper exerciseMapper = new ExerciseMapperImpl();
    ExerciseService exerciseService = new ExerciseService(exerciseRepository, exerciseMapper);

    @Test
    void getExercisesByIds_whenValidListProvided_thenReturnsListOfExercises() {
        var idsList = List.of(UUID.randomUUID());
        var exerciseList = List.of(new ExerciseEntity());

        when(exerciseRepository.findAllById(idsList)).thenReturn(exerciseList);

        assertEquals(1, exerciseService.getExercisesByIds(idsList).size());
        verify(exerciseRepository).findAllById(idsList);
    }

    @Test
    void getExercisesByIds_whenNonExistingUuidProvided_thenReturnsEmptyList() {
        var idsList = List.of(UUID.randomUUID());

        when(exerciseRepository.findAllById(idsList)).thenReturn(List.of());

        assertEquals(0, exerciseService.getExercisesByIds(idsList).size());
        verify(exerciseRepository).findAllById(idsList);
    }


    @Test
    void getAllPossibleExercises_whenCalled_ReturnsAllExercises() {
        var exerciseList = List.of(new ExerciseEntity(), new ExerciseEntity(), new ExerciseEntity());

        when(exerciseRepository.findAll()).thenReturn(exerciseList);

        assertEquals(3, exerciseService.getAllExercises().size());
        verify(exerciseRepository).findAll();
    }
}