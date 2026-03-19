package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {

    UserResponseDTO getUserByUsername(String username);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(String username, UserUpdateDto userUpdateDto);

    void deleteUser(String username);
}
