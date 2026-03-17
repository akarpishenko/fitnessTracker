package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.dto.user.UserUpdateDto;
import fitenessTrackerApp.etities.UserEntity;
import fitenessTrackerApp.exception.EmailAlreadyInUseException;
import fitenessTrackerApp.exception.UserNotFoundException;
import fitenessTrackerApp.exception.UsernameAlreadyInUseException;
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
    public UserResponseDTO getUserByUsername(String username) {
        return userMapper.toUserResponse(userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username)));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userMapper.toUserResponseList(userRepo.findAll());
    }

    @Override
    public UserResponseDTO updateUser(String username, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        if (userUpdateDto.getName() != null) userEntity.setName(userUpdateDto.getName());
        if (userUpdateDto.getLastName() != null) userEntity.setLastName(userUpdateDto.getLastName());

        if (userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().isBlank()) {
            if (userRepo.existsByEmailAndUsernameNot(userUpdateDto.getEmail(), username))
                throw new EmailAlreadyInUseException(userUpdateDto.getEmail());
            userEntity.setEmail(userUpdateDto.getEmail());
        }

        if (userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().isBlank()) {
            if (userRepo.existsByUsernameAndEmailNot(userUpdateDto.getUsername(), userUpdateDto.getEmail()))
                throw new UsernameAlreadyInUseException(userUpdateDto.getUsername());
            userEntity.setUsername(userUpdateDto.getUsername());
        }

        return userMapper.toUserResponse(userRepo.save(userEntity));
    }

    @Override
    public void deleteUser(String username) {
        UserEntity user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        userRepo.deleteById(user.getId());
    }
}
