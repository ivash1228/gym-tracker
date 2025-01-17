package glushkova.kristina.gym_tracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import glushkova.kristina.gym_tracker.exceptions.ExerciseAlreadyExistsOnWorkoutException;
import glushkova.kristina.gym_tracker.models.postModels.CreateWorkoutExerciseRequest;
import glushkova.kristina.gym_tracker.services.WorkoutExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WorkoutExerciseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    WorkoutExerciseService workoutExerciseService;

    @Test
    public void addExerciseToWorkout_whenSameExerciseIdProvided_throw409() throws Exception {

        CreateWorkoutExerciseRequest exerciseId = new CreateWorkoutExerciseRequest(UUID.randomUUID());

        var clientId = UUID.randomUUID();
        var workoutId = UUID.randomUUID();

        when(workoutExerciseService.saveWorkoutExerciseRecord(clientId, workoutId, exerciseId.exerciseId()))
                .thenThrow(ExerciseAlreadyExistsOnWorkoutException.class);

        mockMvc.perform(post("/clients/%s/workouts/%s/exercises".formatted(clientId, workoutId))
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exerciseId)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

}