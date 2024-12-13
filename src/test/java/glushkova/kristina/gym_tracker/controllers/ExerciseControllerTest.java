package glushkova.kristina.gym_tracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import glushkova.kristina.gym_tracker.models.ExerciseModel;
import glushkova.kristina.gym_tracker.models.ExerciseType;
import glushkova.kristina.gym_tracker.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExerciseController.class)
class ExerciseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ExerciseService exerciseService;

//    @Test
//    void getAllPossibleExercises_whenGetAllExercises_thenReturnListOfAllExercises() throws Exception {
//        var exerciseList = List.of(new ExerciseModel(UUID.randomUUID(), "bench press", ExerciseType.SET));
//
//        when(exerciseService.getAllExercises()).thenReturn(exerciseList);
//
//        mockMvc.perform(get("/exercises").with(jwt()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(exerciseList)));
//    }

    @Test
    void getAllPossibleExercises_401() throws Exception {
        mockMvc.perform(get("/exercises"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}