package controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fitenessTrackerApp.FitnessTrackerApp;
import fitenessTrackerApp.controller.WorkoutController;
import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import fitenessTrackerApp.dto.workout.WorkoutUpdateDto;
import fitenessTrackerApp.service.WorkoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkoutController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = FitnessTrackerApp.class)
class WorkoutControllerTest {

    private final long userId = 1L;
    private final long workoutId = 2L;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final LocalDateTime start = LocalDateTime.now().minusHours(10);
    private final LocalDateTime finish = LocalDateTime.now().minusHours(9);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WorkoutService workoutService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createWorkout_shouldReturnWorkout() throws Exception {
        WorkoutCreateDto dto = WorkoutCreateDto.builder()
                .start(start)
                .finish(finish)
                .build();

        WorkoutResponseDTO response = WorkoutResponseDTO.builder()
                .start(start)
                .finish(finish)
                .build();

        when(workoutService.createWorkout(eq(userId), any())).thenReturn(response);

        mockMvc.perform(post("/users/{userId}/workouts", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.start").value(start.format(formatter)))
                .andExpect(jsonPath("$.finish").value(finish.format(formatter)));

        verify(workoutService).createWorkout(eq(userId), any());
    }

    @Test
    void getWorkoutById_shouldReturnWorkout() throws Exception {
        WorkoutResponseDTO response = WorkoutResponseDTO.builder()
                .start(start)
                .finish(finish)
                .build();

        when(workoutService.getWorkoutById(workoutId)).thenReturn(response);

        mockMvc.perform(get("/users/{userId}/workouts/{id}", userId, workoutId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.start").value(start.format(formatter)))
                .andExpect(jsonPath("$.finish").value(finish.format(formatter)));

        verify(workoutService).getWorkoutById(workoutId);
    }

    @Test
    void getWorkoutsByUser_shouldReturnList() throws Exception {
        WorkoutResponseDTO response = WorkoutResponseDTO.builder()
                .start(start)
                .finish(finish)
                .build();

        when(workoutService.getWorkoutsByUser(userId)).thenReturn(List.of(response));

        mockMvc.perform(get("/users/{userId}/workouts", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].start").value(start.format(formatter)))
                .andExpect(jsonPath("$[0].finish").value(finish.format(formatter)));

        verify(workoutService).getWorkoutsByUser(userId);
    }

    @Test
    void updateWorkout_shouldReturnUpdatedWorkout() throws Exception {
        WorkoutUpdateDto dto = WorkoutUpdateDto.builder()
                .start(start)
                .finish(finish)
                .build();

        WorkoutResponseDTO response = WorkoutResponseDTO.builder()
                .start(start)
                .finish(finish)
                .build();

        when(workoutService.updateWorkout(eq(userId), eq(workoutId), any())).thenReturn(response);

        mockMvc.perform(patch("/users/{userId}/workouts/{id}", userId, workoutId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.start").value(start.format(formatter)))
                .andExpect(jsonPath("$.finish").value(finish.format(formatter)));

        verify(workoutService).updateWorkout(eq(userId), eq(workoutId), any());
    }

    @Test
    void deleteWorkout_shouldCallService() throws Exception {
        mockMvc.perform(delete("/users/{userId}/workouts/{id}", userId, workoutId))
                .andExpect(status().isOk());

        verify(workoutService).deleteWorkout(workoutId, userId);
    }
}