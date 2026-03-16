package controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fitenessTrackerApp.FitnessTrackerApp;
import fitenessTrackerApp.controller.UserController;
import fitenessTrackerApp.dto.user.UserCreateDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = FitnessTrackerApp.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_shouldReturnUser() throws Exception {

        UserCreateDto dto = UserCreateDto.builder()
                .name("Anna")
                .lastName("Orange")
                .email("aot@gmail.com")
                .username("aor")
                .build();

        UserResponseDTO response = UserResponseDTO.builder()
                .name("Anna")
                .lastName("Orange")
                .email("aot@gmail.com")
                .username("aor")
                .build();

        when(userService.createUser(any())).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(userService).createUser(any());
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {

        UserResponseDTO response = new UserResponseDTO();
        response.setName("Anna");

        when(userService.getUserById(1)).thenReturn(response);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());

        verify(userService).getUserById(1);
    }

    @Test
    void getAllUsers_shouldReturnList() throws Exception {

        when(userService.getAllUsers()).thenReturn(List.of(new UserResponseDTO()));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }

    @Test
    void updateUser_shouldUpdateUser() throws Exception {

        UserUpdateDto dto = new UserUpdateDto();
        dto.setName("New");

        when(userService.updateUser(eq(1L), any())).thenReturn(new UserResponseDTO());

        mockMvc.perform(patch("/users/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(userService).updateUser(eq(1L), any());
    }
}