package glushkova.kristina.gym_tracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import glushkova.kristina.gym_tracker.exceptions.ExerciseAlreadyExistsOnWorkoutException;
import glushkova.kristina.gym_tracker.models.WorkoutExerciseModel;
import glushkova.kristina.gym_tracker.models.postModels.CreateWorkoutExerciseRequest;
import glushkova.kristina.gym_tracker.services.WorkoutExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkoutExerciseController.class)
class WorkoutExerciseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    WorkoutExerciseService workoutExerciseService;

    @Test
    void addExerciseToWorkout_WhenValidInfoProvided_ThenReturnRecordUuid() throws Exception {
        var clientId = UUID.randomUUID();
        var workoutId = UUID.randomUUID();
        var exerciseId = UUID.randomUUID();
        var recordUuid = UUID.randomUUID();
        var request = new CreateWorkoutExerciseRequest(exerciseId);
        var workoutExerciseModel = new WorkoutExerciseModel(recordUuid, workoutId, exerciseId, 1, null);

        when(workoutExerciseService.saveWorkoutExerciseRecord(clientId, workoutId, exerciseId))
                .thenReturn(workoutExerciseModel);

        mockMvc.perform(post("/clients/%s/workouts/%s/exercises".formatted(clientId, workoutId))
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(recordUuid)));
        verify(workoutExerciseService).saveWorkoutExerciseRecord(clientId, workoutId, exerciseId);
    }

    @Test
    void addExerciseToWorkout_whenSameExerciseIdProvided_throw409() throws Exception {
        var request = new CreateWorkoutExerciseRequest(UUID.randomUUID());
        var clientId = UUID.randomUUID();
        var workoutId = UUID.randomUUID();

        when(workoutExerciseService.saveWorkoutExerciseRecord(clientId, workoutId, request.exerciseId()))
                .thenThrow(ExerciseAlreadyExistsOnWorkoutException.class);

        mockMvc.perform(post("/clients/%s/workouts/%s/exercises".formatted(clientId, workoutId))
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void getWorkoutExercises_WhenValidWorkoutProvided_ThenReturnAllExercises() throws Exception {
        var clientId = UUID.randomUUID();
        var workoutId = UUID.randomUUID();
        var exerciseId = UUID.randomUUID();
        var workoutExercises = List.of(
                new WorkoutExerciseModel(UUID.randomUUID(), workoutId, exerciseId, 1, null));

        when(workoutExerciseService.getAllExercisesByWorkoutId(workoutId)).thenReturn(workoutExercises);

        mockMvc.perform(get("/clients/%s/workouts/%s/exercises".formatted(clientId, workoutId)).with(jwt()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(workoutExercises)));
        verify(workoutExerciseService).getAllExercisesByWorkoutId(workoutId);
    }

}