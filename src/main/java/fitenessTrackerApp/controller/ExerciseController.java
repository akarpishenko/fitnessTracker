package fitenessTrackerApp.controller;


import fitenessTrackerApp.dto.exercise.ExerciseCreateDto;
import fitenessTrackerApp.dto.exercise.ExerciseResponseDTO;
import fitenessTrackerApp.dto.exercise.ExerciseUpdateDto;
import fitenessTrackerApp.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/workouts/{workoutId}/exercises")
@RequiredArgsConstructor
@Validated
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping
    public ExerciseResponseDTO save(@PathVariable long userId, @PathVariable long workoutId, @Valid @RequestBody ExerciseCreateDto exerciseCreateDto) {
        return exerciseService.createExercise(workoutId, exerciseCreateDto);
    }

    @GetMapping("/{id}")
    public ExerciseResponseDTO getById(@PathVariable long userId, @PathVariable long workoutId, @PathVariable long id) {
        return exerciseService.getExerciseById(id);
    }

    @GetMapping
    public List<ExerciseResponseDTO> getExercisesFromWorkout(@PathVariable long userId, @PathVariable long workoutId) {
        return exerciseService.getExercisesByWorkout(workoutId);
    }

    @PatchMapping("/{id}")
    public ExerciseResponseDTO updateById(@PathVariable long userId, @PathVariable long workoutId, @PathVariable long id, @Valid @RequestBody ExerciseUpdateDto exerciseUpdateDto) {
        return exerciseService.updateExercise(id, exerciseUpdateDto, workoutId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long userId, @PathVariable long workoutId, @PathVariable long id) {
        exerciseService.deleteExercise(id, workoutId);
    }
}
