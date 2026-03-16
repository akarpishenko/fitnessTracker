package fitenessTrackerApp.controller;


import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import fitenessTrackerApp.dto.workout.WorkoutUpdateDto;
import fitenessTrackerApp.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/workouts")
@RequiredArgsConstructor
@Validated
public class WorkoutController {
    private final WorkoutService workoutService;

    @PostMapping
    public WorkoutResponseDTO save(@PathVariable long userId, @Valid @RequestBody WorkoutCreateDto workoutCreateDto) {
        return workoutService.createWorkout(userId, workoutCreateDto);
    }

    @GetMapping("/{id}")
    public WorkoutResponseDTO getById(@PathVariable long userId, @PathVariable long id) {
        return workoutService.getWorkoutById(id);
    }

    @GetMapping
    public List<WorkoutResponseDTO> getMyRuns(@PathVariable long userId) {
        return workoutService.getWorkoutsByUser(userId);
    }

    @PatchMapping("/{id}")
    public WorkoutResponseDTO updateById(@PathVariable long userId, @PathVariable long id, @Valid @RequestBody WorkoutUpdateDto workoutUpdateDto) {
        return workoutService.updateWorkout(userId, id, workoutUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long userId, @PathVariable long id) {
        workoutService.deleteWorkout(id, userId);
    }
}
