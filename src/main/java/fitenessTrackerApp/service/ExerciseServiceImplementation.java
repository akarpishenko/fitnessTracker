package fitenessTrackerApp.service;

import fitenessTrackerApp.dto.exercise.ExerciseCreateDto;
import fitenessTrackerApp.dto.exercise.ExerciseResponseDTO;
import fitenessTrackerApp.dto.exercise.ExerciseUpdateDto;
import fitenessTrackerApp.etities.Exercise;
import fitenessTrackerApp.etities.Workout;
import fitenessTrackerApp.mappers.ExerciseMapper;
import fitenessTrackerApp.repository.ExerciseRepo;
import fitenessTrackerApp.repository.WorkoutRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ExerciseServiceImplementation implements ExerciseService {
    private static final double DISTANCE = 0.5;
    private static final double G = 9.81;
    private static final double JOULES_PER_KILOCALORIE = 4184;
    private final ExerciseMapper exerciseMapper;
    private final ExerciseRepo exerciseRepo;
    private final WorkoutRepo workoutRepo;

    @Override
    public ExerciseResponseDTO createExercise(long workoutId, ExerciseCreateDto exerciseCreateDto) {
        Exercise exercise = exerciseMapper.fromExerciseCreateDto(exerciseCreateDto);
        Workout workout = workoutRepo.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found with id " + workoutId));
        exercise.setWorkout(workout);
        workout.setCaloriesBurned(workout.getCaloriesBurned() + calculateCalories(exercise));
        return exerciseMapper.toExerciseResponseDTO(exerciseRepo.save(exercise));
    }

    @Override
    public ExerciseResponseDTO getExerciseById(long exerciseId) {
        return exerciseMapper.toExerciseResponseDTO(exerciseRepo.findById(exerciseId).orElseThrow(() -> new RuntimeException("Exercise not found with id " + exerciseId)));
    }

    @Override
    public List<ExerciseResponseDTO> getExercisesByWorkout(long workoutId) {
        return exerciseMapper.toExerciseResponseDTOList(exerciseRepo.findAllByWorkoutId(workoutId));
    }

    @Override
    public ExerciseResponseDTO updateExercise(long exerciseId, ExerciseUpdateDto exerciseUpdateDto, long workoutId) {
        Exercise exercise = exerciseRepo.findById(exerciseId).orElseThrow(() -> new RuntimeException("Exercise not found with id " + exerciseId));
        Workout workout = workoutRepo.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found with id " + workoutId));
        if (exercise.getWorkout().getId() != workoutId) {
            throw new RuntimeException("Workout id- " + workoutId + " and exercise workout id- " + exercise.getWorkout().getId() + " is not matching");
        }
        workout.setCaloriesBurned(workout.getCaloriesBurned() - calculateCalories(exercise));
        if (exerciseUpdateDto.getName() != null) exercise.setName(exerciseUpdateDto.getName());
        if (exerciseUpdateDto.getReps() != null) exercise.setReps(exerciseUpdateDto.getReps());
        if (exerciseUpdateDto.getWeightKg() != null) exercise.setWeightKg(exerciseUpdateDto.getWeightKg());
        workout.setCaloriesBurned(workout.getCaloriesBurned() + calculateCalories(exercise));
        return exerciseMapper.toExerciseResponseDTO(exerciseRepo.save(exercise));
    }

    @Override
    public void deleteExercise(long exerciseId, long workoutId) {
        Exercise exercise = exerciseRepo.findById(exerciseId).orElseThrow(() -> new RuntimeException("Exercise not found with id " + exerciseId));
        Workout workout = workoutRepo.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found with id " + workoutId));
        if (exercise.getWorkout().getId() != workoutId) {
            throw new RuntimeException("Workout id- " + workoutId + " and exercise workout id- " + exercise.getWorkout().getId() + " is not matching");
        }
        workout.setCaloriesBurned(workout.getCaloriesBurned() - calculateCalories(exercise));
        exerciseRepo.deleteById(exerciseId);
    }

    private double calculateCalories(Exercise exercise) {
        return exercise.getReps() * exercise.getWeightKg() * G * DISTANCE / JOULES_PER_KILOCALORIE;
    }
}
