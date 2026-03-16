package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import fitenessTrackerApp.dto.workout.WorkoutUpdateDto;

import java.util.List;

public interface WorkoutService {
    WorkoutResponseDTO createWorkout(long userId, WorkoutCreateDto workoutCreateDto);

    WorkoutResponseDTO getWorkoutById(long workoutId);

    List<WorkoutResponseDTO> getWorkoutsByUser(long userId);

    WorkoutResponseDTO updateWorkout(long userId, long workoutId, WorkoutUpdateDto workoutUpdateDto);

    void deleteWorkout(long workoutId, long userId);
}
