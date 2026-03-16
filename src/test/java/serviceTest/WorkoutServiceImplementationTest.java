package serviceTest;

import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import fitenessTrackerApp.etities.User;
import fitenessTrackerApp.etities.Workout;
import fitenessTrackerApp.mappers.WorkoutMapper;
import fitenessTrackerApp.repository.UserRepo;
import fitenessTrackerApp.repository.WorkoutRepo;
import fitenessTrackerApp.service.WorkoutServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceImplementationTest {
    private final LocalDateTime start = LocalDateTime.now().minusHours(10);
    private final LocalDateTime finish = LocalDateTime.now().minusHours(9);
    @Mock
    private WorkoutRepo workoutRepo;
    @Mock
    private WorkoutMapper workoutMapper;
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private WorkoutServiceImplementation workoutServiceImplementation;

    @Test
    public void createWorkout_ReturnWorkoutDto() {
        Workout workout = Workout.builder()
                .start(start)
                .finish(finish)
                .build();
        WorkoutCreateDto workoutCreateDto = WorkoutCreateDto.builder()
                .start(start)
                .finish(finish)
                .build();
        WorkoutResponseDTO workoutResponseDTO = WorkoutResponseDTO.builder()
                .start(start)
                .finish(finish)
                .build();
        User user = new User();
        when(workoutMapper.fromWorkoutCreateDto(Mockito.any())).thenReturn(workout);
        when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(workoutRepo.save(Mockito.any())).thenReturn(workout);
        when(workoutMapper.toWorkoutResponseDTO(Mockito.any())).thenReturn(workoutResponseDTO);
        WorkoutResponseDTO savedWorkout = workoutServiceImplementation.createWorkout(1, workoutCreateDto);
        assertNotNull(savedWorkout);
        assertEquals(start, savedWorkout.getStart());
        assertEquals(finish, savedWorkout.getFinish());
        verify(workoutRepo, times(1)).save(workout);

    }

    @Test
    public void getWorkoutById_shouldReturnWorkout() {
        Workout workout = Workout.builder()
                .start(start)
                .finish(finish)
                .build();
        WorkoutResponseDTO workoutResponseDTO = WorkoutResponseDTO.builder()
                .start(start)
                .finish(finish)
                .build();
        when(workoutRepo.findById(Mockito.any())).thenReturn(Optional.of(workout));
        when(workoutMapper.toWorkoutResponseDTO(Mockito.any())).thenReturn(workoutResponseDTO);
        WorkoutResponseDTO returned = workoutServiceImplementation.getWorkoutById(1);
        assertNotNull(returned);
        assertEquals(start, returned.getStart());
        assertEquals(finish, returned.getFinish());
        verify(workoutRepo, times(1)).findById(1L);

    }

    @Test
    public void getWorkoutsByUser_shouldReturnList() {

        Workout workout = Workout.builder()
                .start(start)
                .finish(finish)
                .build();

        WorkoutResponseDTO dto = WorkoutResponseDTO.builder()
                .start(start)
                .finish(finish)
                .build();

        when(workoutRepo.findAllByUserId(Mockito.anyLong()))
                .thenReturn(java.util.List.of(workout));

        when(workoutMapper.toWorkoutResponseDTOList(Mockito.any()))
                .thenReturn(java.util.List.of(dto));

        var result = workoutServiceImplementation.getWorkoutsByUser(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(start, result.get(0).getStart());

        verify(workoutRepo, times(1)).findAllByUserId(1L);
    }

    @Test
    public void updateWorkout_shouldUpdateStartAndFinish() {

        User user = new User();
        user.setId(1);

        Workout workout = Workout.builder()
                .start(start)
                .finish(finish)
                .user(user)
                .build();

        LocalDateTime newStart = start.minusHours(1);
        LocalDateTime newFinish = finish.minusHours(1);

        var updateDto = new fitenessTrackerApp.dto.workout.WorkoutUpdateDto();
        updateDto.setStart(newStart);
        updateDto.setFinish(newFinish);

        WorkoutResponseDTO responseDTO = WorkoutResponseDTO.builder()
                .start(newStart)
                .finish(newFinish)
                .build();

        when(workoutRepo.findById(Mockito.any())).thenReturn(Optional.of(workout));
        when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(workoutRepo.save(Mockito.any())).thenReturn(workout);
        when(workoutMapper.toWorkoutResponseDTO(Mockito.any())).thenReturn(responseDTO);

        WorkoutResponseDTO updated =
                workoutServiceImplementation.updateWorkout(1, 1, updateDto);

        assertEquals(newStart, updated.getStart());
        assertEquals(newFinish, updated.getFinish());

        verify(workoutRepo).save(workout);
    }

    @Test
    public void deleteWorkout_shouldDeleteWorkout() {

        User user = new User();
        user.setId(1);

        Workout workout = Workout.builder()
                .user(user)
                .build();

        when(workoutRepo.findById(Mockito.any()))
                .thenReturn(Optional.of(workout));

        when(userRepo.findById(Mockito.any()))
                .thenReturn(Optional.of(user));

        workoutServiceImplementation.deleteWorkout(1, 1);

        verify(workoutRepo, times(1)).deleteById(1L);
    }

    @Test
    public void createWorkout_shouldThrowIfUserNotFound() {

        WorkoutCreateDto dto = WorkoutCreateDto.builder()
                .start(start)
                .finish(finish)
                .build();

        when(userRepo.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> workoutServiceImplementation.createWorkout(1, dto)
        );

        assertEquals("User not found with id 1", exception.getMessage());
    }

    @Test
    public void updateWorkout_shouldThrowIfUserMismatch() {

        User workoutUser = new User();
        workoutUser.setId(2);

        Workout workout = Workout.builder()
                .user(workoutUser)
                .build();

        User requestUser = new User();
        requestUser.setId(1);

        var updateDto = new fitenessTrackerApp.dto.workout.WorkoutUpdateDto();

        when(workoutRepo.findById(Mockito.any()))
                .thenReturn(Optional.of(workout));

        when(userRepo.findById(Mockito.any()))
                .thenReturn(Optional.of(requestUser));

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> workoutServiceImplementation.updateWorkout(1, 1, updateDto)
        );

        assertNotNull(exception);
    }
}
