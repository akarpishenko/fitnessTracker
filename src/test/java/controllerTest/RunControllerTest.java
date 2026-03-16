package controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fitenessTrackerApp.FitnessTrackerApp;
import fitenessTrackerApp.controller.RunController;
import fitenessTrackerApp.dto.run.RunCreateDto;
import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.dto.run.RunUpdateDto;
import fitenessTrackerApp.service.RunService;
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

@WebMvcTest(RunController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = FitnessTrackerApp.class)
class RunControllerTest {

    private final long userId = 1L;
    private final long runId = 2L;
    private final LocalDateTime start = LocalDateTime.now().minusHours(5);
    private final LocalDateTime finish = LocalDateTime.now().minusHours(4);
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RunService runService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createRun_shouldReturnRun() throws Exception {
        RunCreateDto dto = RunCreateDto.builder()
                .start(start)
                .finish(finish)
                .distanceKm(5.0)
                .averagePace(6.5)
                .build();

        RunResponseDTO response = RunResponseDTO.builder()
                .userId(userId)
                .start(start)
                .finish(finish)
                .distanceKm(5.0)
                .averagePace(6.5)
                .caloriesBurned(300)
                .build();

        when(runService.createRun(eq(userId), any())).thenReturn(response);

        mockMvc.perform(post("/users/{userId}/runs", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.start").value(start.format(formatter)))
                .andExpect(jsonPath("$.finish").value(finish.format(formatter)))
                .andExpect(jsonPath("$.distanceKm").value(5.0))
                .andExpect(jsonPath("$.averagePace").value(6.5))
                .andExpect(jsonPath("$.caloriesBurned").value(300));

        verify(runService).createRun(eq(userId), any());
    }

    @Test
    void getRunById_shouldReturnRun() throws Exception {
        RunResponseDTO response = RunResponseDTO.builder()
                .userId(userId)
                .start(start)
                .finish(finish)
                .distanceKm(5.0)
                .averagePace(6.5)
                .caloriesBurned(300)
                .build();

        when(runService.getRunById(runId)).thenReturn(response);

        mockMvc.perform(get("/users/{userId}/runs/{id}", userId, runId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.start").value(start.format(formatter)))
                .andExpect(jsonPath("$.finish").value(finish.format(formatter)))
                .andExpect(jsonPath("$.distanceKm").value(5.0));

        verify(runService).getRunById(runId);
    }

    @Test
    void getRunsByUser_shouldReturnList() throws Exception {
        RunResponseDTO response = RunResponseDTO.builder()
                .userId(userId)
                .start(start)
                .finish(finish)
                .distanceKm(5.0)
                .averagePace(6.5)
                .caloriesBurned(300)
                .build();

        when(runService.getRunsByUser(userId)).thenReturn(List.of(response));

        mockMvc.perform(get("/users/{userId}/runs", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].start").value(start.format(formatter)))
                .andExpect(jsonPath("$[0].distanceKm").value(5.0));

        verify(runService).getRunsByUser(userId);
    }

    @Test
    void updateRun_shouldReturnUpdatedRun() throws Exception {
        RunUpdateDto dto = RunUpdateDto.builder()
                .start(start)
                .finish(finish)
                .distanceKm(6.0)
                .averagePace(6.0)
                .build();

        RunResponseDTO response = RunResponseDTO.builder()
                .userId(userId)
                .start(start)
                .finish(finish)
                .distanceKm(6.0)
                .averagePace(6.0)
                .caloriesBurned(350)
                .build();

        when(runService.updateRun(eq(userId), eq(runId), any())).thenReturn(response);

        mockMvc.perform(patch("/users/{userId}/runs/{id}", userId, runId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.distanceKm").value(6.0))
                .andExpect(jsonPath("$.averagePace").value(6.0))
                .andExpect(jsonPath("$.caloriesBurned").value(350));

        verify(runService).updateRun(eq(userId), eq(runId), any());
    }

    @Test
    void deleteRun_shouldCallService() throws Exception {
        mockMvc.perform(delete("/users/{userId}/runs/{id}", userId, runId))
                .andExpect(status().isOk());

        verify(runService).deleteRun(userId, runId);
    }
}