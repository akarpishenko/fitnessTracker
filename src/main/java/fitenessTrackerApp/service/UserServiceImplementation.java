package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.user.UserCreateDto;
import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.dto.user.UserUpdateDto;
import fitenessTrackerApp.etities.User;
import fitenessTrackerApp.mappers.UserMapper;
import fitenessTrackerApp.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImplementation implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserCreateDto userCreateDto) {
        return userMapper.toUserResponse(userRepo.save(userMapper.fromUserCreateDto(userCreateDto)));
    }

    @Override
    public UserResponseDTO getUserById(long userId) {
        return userMapper.toUserResponse(userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId)));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userMapper.toUserResponseList(userRepo.findAll());
    }

    @Override
    public UserResponseDTO updateUser(long userId, UserUpdateDto userUpdateDto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userUpdateDto.getName() != null) user.setName(userUpdateDto.getName());
        if (userUpdateDto.getLastName() != null) user.setLastName(userUpdateDto.getLastName());

        if (userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().isBlank()) {
            if (userRepo.existsByEmailAndIdNot(userUpdateDto.getEmail(), userId))
                throw new RuntimeException("Email already in use");
            user.setEmail(userUpdateDto.getEmail());
        }

        if (userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().isBlank()) {
            if (userRepo.existsByUsernameAndIdNot(userUpdateDto.getUsername(), userId))
                throw new RuntimeException("Username already in use");
            user.setUsername(userUpdateDto.getUsername());
        }

        return userMapper.toUserResponse(userRepo.save(user));
    }

    @Override
    public void deleteUser(long userId) {
        userRepo.deleteById(userId);
    }
}
