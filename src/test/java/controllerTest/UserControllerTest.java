package controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fitenessTrackerApp.FitnessTrackerApp;
import fitenessTrackerApp.controller.UserController;
import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.dto.user.UserUpdateDto;
import fitenessTrackerApp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = FitnessTrackerApp.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getByUsername_shouldReturnUser() throws Exception {

        UserResponseDTO response = new UserResponseDTO();
        response.setName("Anna");

        when(userService.getUserByUsername("anna")).thenReturn(response);

        mockMvc.perform(get("/users/anna").with(user("anna")))
                .andExpect(status().isOk());

        verify(userService).getUserByUsername("anna");
    }

    @Test
    void getAll_shouldReturnList() throws Exception {

        when(userService.getAllUsers()).thenReturn(List.of(new UserResponseDTO()));

        mockMvc.perform(get("/users").with(user("anna")))
                .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }

    @Test
    void update_shouldUpdateUser() throws Exception {

        UserUpdateDto dto = new UserUpdateDto();
        dto.setName("New");

        when(userService.updateUser(eq("anna"), any()))
                .thenReturn(new UserResponseDTO());

        mockMvc.perform(patch("/users")
                        .with(user("anna"))
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(userService).updateUser(eq("anna"), any());
    }
}