package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import fitenessTrackerApp.dto.workout.WorkoutUpdateDto;

import java.util.List;

public interface WorkoutService {
    WorkoutResponseDTO createWorkout(String username, WorkoutCreateDto workoutCreateDto);

    WorkoutResponseDTO getWorkoutById(long workoutId);

    List<WorkoutResponseDTO> getWorkoutsByUser(String username);

    WorkoutResponseDTO updateWorkout(String username, long workoutId, WorkoutUpdateDto workoutUpdateDto);

    void deleteWorkout(long workoutId, String username);
}
