package fitenessTrackerApp.controller;


import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import fitenessTrackerApp.dto.workout.WorkoutUpdateDto;
import fitenessTrackerApp.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users/workouts")
@RequiredArgsConstructor
@Validated
public class WorkoutController {
    private final WorkoutService workoutService;

    @PostMapping
    public WorkoutResponseDTO save(Authentication authentication, @Valid @RequestBody WorkoutCreateDto workoutCreateDto) {
        return workoutService.createWorkout(authentication.getName(), workoutCreateDto);
    }

    @GetMapping("/{id}")
    public WorkoutResponseDTO getById(@PathVariable long id) {
        return workoutService.getWorkoutById(id);
    }

    @GetMapping
    public List<WorkoutResponseDTO> getMyWorkouts(Authentication authentication) {
        return workoutService.getWorkoutsByUser(authentication.getName());
    }

    @PatchMapping("/{id}")
    public WorkoutResponseDTO updateById(Authentication authentication, @PathVariable long id, @Valid @RequestBody WorkoutUpdateDto workoutUpdateDto) {
        return workoutService.updateWorkout(authentication.getName(), id, workoutUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(Authentication authentication, @PathVariable long id) {
        workoutService.deleteWorkout(id, authentication.getName());
    }
}
