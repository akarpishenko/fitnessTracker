package fitenessTrackerApp.controller;

import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.dto.user.UserUpdateDto;
import fitenessTrackerApp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public UserResponseDTO getByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping
    public List<UserResponseDTO> getAll() {
        return userService.getAllUsers();
    }

    @PatchMapping
    public UserResponseDTO update(Authentication authentication, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(authentication.getName(), userUpdateDto);
    }
}
