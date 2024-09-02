package glushkova.kristina.gym_tracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import glushkova.kristina.gym_tracker.models.CreateWorkoutRequest;
import glushkova.kristina.gym_tracker.models.ExerciseUuid;
import glushkova.kristina.gym_tracker.models.WorkoutModel;
import glushkova.kristina.gym_tracker.services.ExerciseService;
import glushkova.kristina.gym_tracker.services.WorkoutExerciseService;
import glushkova.kristina.gym_tracker.services.WorkoutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkoutController.class)
class WorkoutControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    WorkoutService workoutService;
    @MockBean
    ExerciseService exerciseService;
    @MockBean
    WorkoutExerciseService workoutExerciseService;

    @Test
    void addWorkout_WhenValidBodyIsProvided_ThenShouldAddWorkout() throws Exception {
        var clientId = UUID.randomUUID();
        var workoutId = UUID.randomUUID();
        var createWorkoutRequest = new CreateWorkoutRequest(LocalDate.now(), "Upper body");

        when(workoutService.addWorkout(clientId, createWorkoutRequest))
                .thenReturn(workoutId);

        mockMvc.perform(post("/clients/%s/workouts".formatted(clientId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createWorkoutRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(workoutId)));
        verify(workoutService).addWorkout(clientId, createWorkoutRequest);
    }

    @ParameterizedTest
    @MethodSource
    void addWorkout_WhenInvalidBodyIsProvided_ThenShouldReturnBadRequest(CreateWorkoutRequest createWorkoutRequest) throws Exception {
        mockMvc.perform(post("/clients/%s/workouts".formatted(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createWorkoutRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addWorkout_WhenInvalidClientIdIsProvided_ThenShouldReturnClientNotFound(){

    }

    @Test
    void getAllWorkoutsByClientId_WhenSuccess_ThenShouldReturnAllWorkoutsForClient() throws Exception {
        var clientId = UUID.randomUUID();
        var workoutsList = List.of(new WorkoutModel(UUID.randomUUID(), clientId, LocalDate.now(), "Upper body"));
        when(workoutService.getAllWorkoutsByClientId(clientId)).thenReturn(workoutsList);

        mockMvc.perform(get("/clients/%s/workouts".formatted(clientId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(workoutsList)));
        verify(workoutService).getAllWorkoutsByClientId(clientId);
    }

    @Test
    void getAllWorkoutsByClientId_WhenInvalidClientIdIsProvided_ThenShouldReturnClientNotFound(){

    }
    @Test
    void addExerciseToWorkout_WhenValidInfoProvided_ThenReturnRecordUuid() throws Exception {
        var clientId = UUID.randomUUID();
        var workoutId = UUID.randomUUID();
        var recordUuid = UUID.randomUUID();
        var exerciseId = new ExerciseUuid(UUID.randomUUID());

        when(workoutExerciseService.saveWorkoutExerciseRecord(workoutId, exerciseId.id()))
                .thenReturn(recordUuid);

        mockMvc.perform(post("/clients/%s/workouts/%s".formatted(clientId, workoutId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exerciseId)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(recordUuid)));
        verify(workoutExerciseService).saveWorkoutExerciseRecord(workoutId, exerciseId.id());
    }

    @Test
    void getAllExercisesForWorkout_WhenValidWorkoutProvided_ThenAllExercisesForWorkoutReturned() {



    }

    private static Stream<CreateWorkoutRequest> addWorkout_WhenInvalidBodyIsProvided_ThenShouldReturnBadRequest() {
        return Stream.of(
                new CreateWorkoutRequest(LocalDate.now(), null),
                new CreateWorkoutRequest(LocalDate.now(), ""),
                new CreateWorkoutRequest(null, "Upper body"),
                new CreateWorkoutRequest(LocalDate.ofYearDay(2023, 23), "Upper body")
        );
    }
}