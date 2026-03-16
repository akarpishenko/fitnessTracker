package controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fitenessTrackerApp.FitnessTrackerApp;
import fitenessTrackerApp.controller.ExerciseController;
import fitenessTrackerApp.dto.exercise.ExerciseCreateDto;
import fitenessTrackerApp.dto.exercise.ExerciseResponseDTO;
import fitenessTrackerApp.dto.exercise.ExerciseUpdateDto;
import fitenessTrackerApp.service.ExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExerciseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = FitnessTrackerApp.class)
class ExerciseControllerTest {

    private final long userId = 1L;
    private final long workoutId = 2L;
    private final long exerciseId = 3L;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExerciseService exerciseService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createExercise_shouldReturnExercise() throws Exception {
        ExerciseCreateDto dto = new ExerciseCreateDto();
        dto.setName("Push Up");
        dto.setReps(10);
        dto.setWeightKg(0.0);

        ExerciseResponseDTO response = new ExerciseResponseDTO();
        response.setName("Push Up");
        response.setReps(10);
        response.setWeightKg(0.0);

        when(exerciseService.createExercise(eq(workoutId), any())).thenReturn(response);

        mockMvc.perform(post("/users/{userId}/workouts/{workoutId}/exercises", userId, workoutId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Push Up"))
                .andExpect(jsonPath("$.reps").value(10));

        verify(exerciseService).createExercise(eq(workoutId), any());
    }

    @Test
    void getExerciseById_shouldReturnExercise() throws Exception {
        ExerciseResponseDTO response = new ExerciseResponseDTO();
        response.setName("Squat");
        response.setReps(15);

        when(exerciseService.getExerciseById(exerciseId)).thenReturn(response);

        mockMvc.perform(get("/users/{userId}/workouts/{workoutId}/exercises/{id}", userId, workoutId, exerciseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Squat"))
                .andExpect(jsonPath("$.reps").value(15));

        verify(exerciseService).getExerciseById(exerciseId);
    }

    @Test
    void getExercisesFromWorkout_shouldReturnList() throws Exception {
        ExerciseResponseDTO response = new ExerciseResponseDTO();
        response.setName("Sit Up");

        when(exerciseService.getExercisesByWorkout(workoutId)).thenReturn(List.of(response));

        mockMvc.perform(get("/users/{userId}/workouts/{workoutId}/exercises", userId, workoutId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sit Up"));

        verify(exerciseService).getExercisesByWorkout(workoutId);
    }

    @Test
    void updateExercise_shouldReturnUpdatedExercise() throws Exception {
        ExerciseUpdateDto dto = new ExerciseUpdateDto();
        dto.setReps(20);

        ExerciseResponseDTO response = new ExerciseResponseDTO();
        response.setName("Push Up");
        response.setReps(20);

        when(exerciseService.updateExercise(eq(exerciseId), any(), eq(workoutId))).thenReturn(response);

        mockMvc.perform(patch("/users/{userId}/workouts/{workoutId}/exercises/{id}", userId, workoutId, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reps").value(20));

        verify(exerciseService).updateExercise(eq(exerciseId), any(), eq(workoutId));
    }

    @Test
    void deleteExercise_shouldCallService() throws Exception {
        mockMvc.perform(delete("/users/{userId}/workouts/{workoutId}/exercises/{id}", userId, workoutId, exerciseId))
                .andExpect(status().isOk());

        verify(exerciseService).deleteExercise(exerciseId, workoutId);
    }
}