package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.user.UserCreateDto;
import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserCreateDto userCreateDto);

    UserResponseDTO getUserById(long userId);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(long userId, UserUpdateDto userUpdateDto);

    void deleteUser(long userId);
}
