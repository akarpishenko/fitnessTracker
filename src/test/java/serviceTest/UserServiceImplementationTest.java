package serviceTest;

import fitenessTrackerApp.dto.user.UserCreateDto;
import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.dto.user.UserUpdateDto;
import fitenessTrackerApp.etities.User;
import fitenessTrackerApp.mappers.UserMapper;
import fitenessTrackerApp.repository.UserRepo;
import fitenessTrackerApp.service.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplementationTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImplementation userServiceImplementation;

    @Test
    public void createUser_shouldReturnUserDto() {

        User user = new User();
        user.setId(1);
        user.setName("John");

        UserCreateDto createDto = new UserCreateDto();
        createDto.setName("John");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setName("John");

        when(userMapper.fromUserCreateDto(Mockito.any())).thenReturn(user);
        when(userRepo.save(Mockito.any())).thenReturn(user);
        when(userMapper.toUserResponse(Mockito.any())).thenReturn(responseDTO);

        UserResponseDTO savedUser = userServiceImplementation.createUser(createDto);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getName());

        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void getUserById_shouldReturnUser() {

        User user = new User();
        user.setId(1);
        user.setName("Anna");

        UserResponseDTO dto = new UserResponseDTO();
        dto.setName("Anna");

        when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(Mockito.any())).thenReturn(dto);

        UserResponseDTO returned = userServiceImplementation.getUserById(1);

        assertNotNull(returned);
        assertEquals("Anna", returned.getName());

        verify(userRepo, times(1)).findById(1L);
    }

    @Test
    public void getAllUsers_shouldReturnUsers() {

        User user = new User();
        user.setName("Max");

        UserResponseDTO dto = new UserResponseDTO();
        dto.setName("Max");

        when(userRepo.findAll()).thenReturn(List.of(user));
        when(userMapper.toUserResponseList(Mockito.any())).thenReturn(List.of(dto));

        List<UserResponseDTO> users = userServiceImplementation.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());

        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void updateUser_shouldUpdateUser() {

        User user = new User();
        user.setId(1);
        user.setName("Old");

        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setName("New");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setName("New");

        when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(userRepo.save(Mockito.any())).thenReturn(user);
        when(userMapper.toUserResponse(Mockito.any())).thenReturn(responseDTO);

        UserResponseDTO updated =
                userServiceImplementation.updateUser(1, updateDto);

        assertEquals("New", updated.getName());

        verify(userRepo).save(user);
    }

    @Test
    public void deleteUser_shouldDeleteUser() {

        userServiceImplementation.deleteUser(1);

        verify(userRepo, times(1)).deleteById(1L);
    }

    @Test
    public void getUserById_shouldThrowException_whenUserNotFound() {

        when(userRepo.findById(Mockito.any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userServiceImplementation.getUserById(1)
        );

        assertEquals("User not found with id 1", exception.getMessage());
    }

    @Test
    public void updateUser_shouldThrowException_whenUserNotFound() {

        UserUpdateDto updateDto = new UserUpdateDto();

        when(userRepo.findById(Mockito.any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userServiceImplementation.updateUser(1, updateDto)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void updateUser_shouldThrowException_whenEmailAlreadyExists() {

        User user = new User();
        user.setId(1);

        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setEmail("test@mail.com");

        when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(userRepo.existsByEmailAndIdNot(Mockito.any(), Mockito.anyLong())).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userServiceImplementation.updateUser(1, updateDto)
        );

        assertEquals("Email already in use", exception.getMessage());
    }

    @Test
    public void updateUser_shouldThrowException_whenUsernameAlreadyExists() {

        User user = new User();
        user.setId(1);

        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setUsername("existingUser");

        when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(userRepo.existsByUsernameAndIdNot(Mockito.any(), Mockito.anyLong())).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userServiceImplementation.updateUser(1, updateDto)
        );

        assertEquals("Username already in use", exception.getMessage());
    }
}