package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.exercise.ExerciseCreateDto;
import fitenessTrackerApp.dto.exercise.ExerciseResponseDTO;
import fitenessTrackerApp.dto.exercise.ExerciseUpdateDto;

import java.util.List;

public interface ExerciseService {
    ExerciseResponseDTO createExercise(long workoutId, ExerciseCreateDto exerciseCreateDto);

    ExerciseResponseDTO getExerciseById(long exerciseId);

    List<ExerciseResponseDTO> getExercisesByWorkout(long workoutId);

    ExerciseResponseDTO updateExercise(long exerciseId, ExerciseUpdateDto exerciseUpdateDto, long workoutId);

    void deleteExercise(long exerciseId, long workoutId);
}
