package serviceTest;

import fitenessTrackerApp.dto.exercise.ExerciseCreateDto;
import fitenessTrackerApp.dto.exercise.ExerciseResponseDTO;
import fitenessTrackerApp.dto.exercise.ExerciseUpdateDto;
import fitenessTrackerApp.etities.Exercise;
import fitenessTrackerApp.etities.Workout;
import fitenessTrackerApp.exception.ExerciseNotFoundException;
import fitenessTrackerApp.exception.WorkoutNotFoundException;
import fitenessTrackerApp.mappers.ExerciseMapper;
import fitenessTrackerApp.repository.ExerciseRepo;
import fitenessTrackerApp.repository.WorkoutRepo;
import fitenessTrackerApp.service.ExerciseServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceImplementationTest {

    @Mock
    private ExerciseRepo exerciseRepo;

    @Mock
    private WorkoutRepo workoutRepo;

    @Mock
    private ExerciseMapper exerciseMapper;

    @InjectMocks
    private ExerciseServiceImplementation exerciseServiceImplementation;

    @Test
    public void createExercise_shouldReturnExerciseDto() {

        Workout workout = new Workout();
        workout.setId(1);
        workout.setCaloriesBurned(0);

        Exercise exercise = Exercise.builder()
                .name("Bench Press")
                .reps(10)
                .weightKg(50)
                .build();

        ExerciseCreateDto createDto = ExerciseCreateDto.builder()
                .name("Bench Press")
                .reps(10)
                .weightKg(50)
                .build();

        ExerciseResponseDTO responseDTO = ExerciseResponseDTO.builder()
                .name("Bench Press")
                .reps(10)
                .weightKg(50)
                .build();

        when(exerciseMapper.fromExerciseCreateDto(Mockito.any())).thenReturn(exercise);
        when(workoutRepo.findById(Mockito.any())).thenReturn(Optional.of(workout));
        when(exerciseRepo.save(Mockito.any())).thenReturn(exercise);
        when(exerciseMapper.toExerciseResponseDTO(Mockito.any())).thenReturn(responseDTO);

        ExerciseResponseDTO saved =
                exerciseServiceImplementation.createExercise(1, createDto);

        assertNotNull(saved);
        assertEquals("Bench Press", saved.getName());

        verify(exerciseRepo, times(1)).save(exercise);
    }

    @Test
    public void getExerciseById_shouldReturnExercise() {

        Exercise exercise = Exercise.builder()
                .name("Squat")
                .reps(8)
                .weightKg(80)
                .build();

        ExerciseResponseDTO dto = ExerciseResponseDTO.builder()
                .name("Squat")
                .reps(8)
                .weightKg(80)
                .build();

        when(exerciseRepo.findById(Mockito.any())).thenReturn(Optional.of(exercise));
        when(exerciseMapper.toExerciseResponseDTO(Mockito.any())).thenReturn(dto);

        ExerciseResponseDTO returned =
                exerciseServiceImplementation.getExerciseById(1);

        assertNotNull(returned);
        assertEquals("Squat", returned.getName());

        verify(exerciseRepo, times(1)).findById(1L);
    }

    @Test
    public void getExercisesByWorkout_shouldReturnExercises() {

        Exercise exercise = Exercise.builder()
                .name("Deadlift")
                .reps(5)
                .weightKg(100)
                .build();

        ExerciseResponseDTO dto = ExerciseResponseDTO.builder()
                .name("Deadlift")
                .reps(5)
                .weightKg(100)
                .build();

        when(exerciseRepo.findAllByWorkoutId(1)).thenReturn(List.of(exercise));
        when(exerciseMapper.toExerciseResponseDTOList(Mockito.any())).thenReturn(List.of(dto));

        List<ExerciseResponseDTO> exercises =
                exerciseServiceImplementation.getExercisesByWorkout(1);

        assertNotNull(exercises);
        assertEquals(1, exercises.size());

        verify(exerciseRepo, times(1)).findAllByWorkoutId(1L);
    }

    @Test
    public void updateExercise_shouldUpdateExercise() {

        Workout workout = new Workout();
        workout.setId(1);
        workout.setCaloriesBurned(100);

        Exercise exercise = Exercise.builder()
                .name("Bench Press")
                .reps(10)
                .weightKg(50)
                .workout(workout)
                .build();

        ExerciseUpdateDto updateDto = new ExerciseUpdateDto();
        updateDto.setReps(12);

        ExerciseResponseDTO responseDTO = ExerciseResponseDTO.builder()
                .name("Bench Press")
                .reps(12)
                .weightKg(50)
                .build();

        when(exerciseRepo.findById(Mockito.any())).thenReturn(Optional.of(exercise));
        when(workoutRepo.findById(Mockito.any())).thenReturn(Optional.of(workout));
        when(exerciseRepo.save(Mockito.any())).thenReturn(exercise);
        when(exerciseMapper.toExerciseResponseDTO(Mockito.any())).thenReturn(responseDTO);

        ExerciseResponseDTO updated =
                exerciseServiceImplementation.updateExercise(1, updateDto, 1);

        assertEquals(12, updated.getReps());

        verify(exerciseRepo).save(exercise);
    }

    @Test
    public void deleteExercise_shouldDeleteExercise() {

        Workout workout = new Workout();
        workout.setId(1);
        workout.setCaloriesBurned(100);

        Exercise exercise = Exercise.builder()
                .reps(10)
                .weightKg(50)
                .workout(workout)
                .build();

        when(exerciseRepo.findById(Mockito.any())).thenReturn(Optional.of(exercise));
        when(workoutRepo.findById(Mockito.any())).thenReturn(Optional.of(workout));

        exerciseServiceImplementation.deleteExercise(1, 1);

        verify(exerciseRepo, times(1)).deleteById(1L);
    }

    @Test
    public void createExercise_shouldThrowException_whenWorkoutNotFound() {

        ExerciseCreateDto dto = ExerciseCreateDto.builder()
                .name("Bench")
                .reps(10)
                .weightKg(50)
                .build();

        Exercise exercise = Exercise.builder()
                .name("Bench")
                .reps(10)
                .weightKg(50)
                .build();

        when(exerciseMapper.fromExerciseCreateDto(Mockito.any())).thenReturn(exercise);
        when(workoutRepo.findById(Mockito.any())).thenReturn(Optional.empty());

        WorkoutNotFoundException exception = assertThrows(
                WorkoutNotFoundException.class,
                () -> exerciseServiceImplementation.createExercise(1, dto)
        );

        assertEquals("Workout with id '1' not found.", exception.getMessage());
    }

    @Test
    public void getExerciseById_shouldThrowException_whenExerciseNotFound() {

        when(exerciseRepo.findById(Mockito.any())).thenReturn(Optional.empty());

        ExerciseNotFoundException exception = assertThrows(
                ExerciseNotFoundException.class,
                () -> exerciseServiceImplementation.getExerciseById(1)
        );

        assertEquals("Exercise with id '1' not found.", exception.getMessage());
    }

    @Test
    public void updateExercise_shouldThrowException_whenWorkoutMismatch() {

        Workout workout = new Workout();
        workout.setId(2);

        Exercise exercise = Exercise.builder()
                .workout(workout)
                .build();

        ExerciseUpdateDto updateDto = new ExerciseUpdateDto();

        when(exerciseRepo.findById(Mockito.any())).thenReturn(Optional.of(exercise));
        when(workoutRepo.findById(Mockito.any())).thenReturn(Optional.of(workout));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> exerciseServiceImplementation.updateExercise(1, updateDto, 1)
        );

        assertTrue(exception.getMessage().contains("is not matching"));
    }

    @Test
    public void deleteExercise_shouldThrowException_whenWorkoutMismatch() {

        Workout workout = new Workout();
        workout.setId(2);

        Exercise exercise = Exercise.builder()
                .workout(workout)
                .build();

        when(exerciseRepo.findById(Mockito.any())).thenReturn(Optional.of(exercise));
        when(workoutRepo.findById(Mockito.any())).thenReturn(Optional.of(workout));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> exerciseServiceImplementation.deleteExercise(1, 1)
        );

        assertTrue(exception.getMessage().contains("is not matching"));
    }
}
