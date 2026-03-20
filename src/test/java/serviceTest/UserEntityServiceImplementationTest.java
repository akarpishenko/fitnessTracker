package serviceTest;

import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.dto.user.UserUpdateDto;
import fitenessTrackerApp.etities.UserEntity;
import fitenessTrackerApp.exception.EmailAlreadyInUseException;
import fitenessTrackerApp.exception.UserNotFoundException;
import fitenessTrackerApp.exception.UsernameAlreadyInUseException;
import fitenessTrackerApp.mappers.UserMapper;
import fitenessTrackerApp.repository.UserRepo;
import fitenessTrackerApp.service.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceImplementationTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImplementation userService;

    @Test
    public void getUserByUsername_shouldReturnUser() {
        UserEntity user = new UserEntity();
        user.setUsername("ann");

        UserResponseDTO dto = new UserResponseDTO();
        dto.setUsername("ann");

        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(dto);

        UserResponseDTO result = userService.getUserByUsername("ann");

        assertNotNull(result);
        assertEquals("ann", result.getUsername());

        verify(userRepo).findByUsername("ann");
    }

    @Test
    public void getUserByUsername_shouldThrow_whenNotFound() {
        when(userRepo.findByUsername("ann")).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> userService.getUserByUsername("ann"));

        assertTrue(ex.getMessage().contains("ann"));
    }

    @Test
    public void getAllUsers_shouldReturnList() {
        UserEntity user = new UserEntity();
        UserResponseDTO dto = new UserResponseDTO();

        when(userRepo.findAll()).thenReturn(List.of(user));
        when(userMapper.toUserResponseList(List.of(user))).thenReturn(List.of(dto));

        List<UserResponseDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepo).findAll();
    }

    @Test
    public void updateUser_shouldUpdateFields() {
        UserEntity user = new UserEntity();
        user.setUsername("ann");
        user.setName("Old");

        UserUpdateDto dto = new UserUpdateDto();
        dto.setName("New");

        UserResponseDTO response = new UserResponseDTO();
        response.setName("New");

        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(response);

        UserResponseDTO result = userService.updateUser("ann", dto);

        assertEquals("New", result.getName());
        verify(userRepo).save(user);
    }

    @Test
    public void updateUser_shouldThrow_whenUserNotFound() {
        when(userRepo.findByUsername("ann")).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> userService.updateUser("ann", new UserUpdateDto()));

        assertEquals("User with username 'ann' not found.", ex.getMessage());
    }

    @Test
    public void updateUser_shouldThrow_whenEmailExists() {
        UserEntity user = new UserEntity();
        user.setUsername("ann");

        UserUpdateDto dto = new UserUpdateDto();
        dto.setEmail("test@mail.com");

        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(user));
        when(userRepo.existsByEmailAndUsernameNot("test@mail.com", "ann")).thenReturn(true);

        EmailAlreadyInUseException ex = assertThrows(EmailAlreadyInUseException.class,
                () -> userService.updateUser("ann", dto));

        assertEquals("Email already in use: test@mail.com", ex.getMessage());
    }

    @Test
    public void updateUser_shouldThrow_whenUsernameExists() {
        UserEntity user = new UserEntity();
        user.setUsername("ann");

        UserUpdateDto dto = new UserUpdateDto();
        dto.setUsername("newUsername");
        dto.setEmail("ann@mail.com"); // needed for your repo method

        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(user));
        when(userRepo.existsByUsernameAndEmailNot("newUsername", "ann@mail.com"))
                .thenReturn(true);

        UsernameAlreadyInUseException ex = assertThrows(UsernameAlreadyInUseException.class,
                () -> userService.updateUser("ann", dto));

        assertEquals("Username already in use: newUsername", ex.getMessage());
    }

    @Test
    public void deleteUser_shouldDelete() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("ann");

        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(user));

        userService.deleteUser("ann");

        verify(userRepo).deleteById(1L);
    }

    @Test
    public void deleteUser_shouldThrow_whenNotFound() {
        when(userRepo.findByUsername("ann")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> userService.deleteUser("ann"));
    }
}