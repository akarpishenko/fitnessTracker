package serviceTest;

import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import fitenessTrackerApp.dto.workout.WorkoutUpdateDto;
import fitenessTrackerApp.etities.UserEntity;
import fitenessTrackerApp.etities.Workout;
import fitenessTrackerApp.exception.UserNotFoundException;
import fitenessTrackerApp.mappers.WorkoutMapper;
import fitenessTrackerApp.repository.UserRepo;
import fitenessTrackerApp.repository.WorkoutRepo;
import fitenessTrackerApp.service.WorkoutServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private WorkoutServiceImplementation workoutService;

    @Test
    public void createWorkout_shouldReturnDto() {
        Workout workout = Workout.builder().start(start).finish(finish).build();

        WorkoutCreateDto dto = WorkoutCreateDto.builder()
                .start(start)
                .finish(finish)
                .build();

        WorkoutResponseDTO response = WorkoutResponseDTO.builder()
                .start(start)
                .finish(finish)
                .build();

        UserEntity user = new UserEntity();
        user.setUsername("ann");

        when(workoutMapper.fromWorkoutCreateDto(dto)).thenReturn(workout);
        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(user));
        when(workoutRepo.save(workout)).thenReturn(workout);
        when(workoutMapper.toWorkoutResponseDTO(workout)).thenReturn(response);

        WorkoutResponseDTO result = workoutService.createWorkout("ann", dto);

        assertNotNull(result);
        assertEquals(start, result.getStart());
        verify(workoutRepo).save(workout);
    }

    @Test
    public void createWorkout_shouldThrow_whenUserNotFound() {
        WorkoutCreateDto dto = WorkoutCreateDto.builder().build();

        when(userRepo.findByUsername("ann")).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> workoutService.createWorkout("ann", dto));

        assertTrue(ex.getMessage().contains("ann"));
    }

    @Test
    public void getWorkoutById_shouldReturnWorkout() {
        Workout workout = Workout.builder().start(start).finish(finish).build();
        WorkoutResponseDTO dto = WorkoutResponseDTO.builder().start(start).finish(finish).build();

        when(workoutRepo.findById(1L)).thenReturn(Optional.of(workout));
        when(workoutMapper.toWorkoutResponseDTO(workout)).thenReturn(dto);

        WorkoutResponseDTO result = workoutService.getWorkoutById(1);

        assertEquals(start, result.getStart());
        verify(workoutRepo).findById(1L);
    }

    @Test
    public void getWorkoutsByUser_shouldReturnList() {
        Workout workout = new Workout();
        WorkoutResponseDTO dto = new WorkoutResponseDTO();

        when(workoutRepo.findAllByUserEntityUsername("ann"))
                .thenReturn(List.of(workout));

        when(workoutMapper.toWorkoutResponseDTOList(List.of(workout)))
                .thenReturn(List.of(dto));

        var result = workoutService.getWorkoutsByUser("ann");

        assertEquals(1, result.size());
        verify(workoutRepo).findAllByUserEntityUsername("ann");
    }

    @Test
    public void updateWorkout_shouldUpdateFields() {
        UserEntity user = new UserEntity();
        user.setUsername("ann");

        Workout workout = Workout.builder()
                .start(start)
                .finish(finish)
                .userEntity(user)
                .build();

        LocalDateTime newStart = start.minusHours(1);
        LocalDateTime newFinish = finish.minusHours(1);

        WorkoutUpdateDto dto = new WorkoutUpdateDto();
        dto.setStart(newStart);
        dto.setFinish(newFinish);

        WorkoutResponseDTO response = WorkoutResponseDTO.builder()
                .start(newStart)
                .finish(newFinish)
                .build();

        when(workoutRepo.findById(1L)).thenReturn(Optional.of(workout));
        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(user));
        when(workoutRepo.save(workout)).thenReturn(workout);
        when(workoutMapper.toWorkoutResponseDTO(workout)).thenReturn(response);

        WorkoutResponseDTO result = workoutService.updateWorkout("ann", 1, dto);

        assertEquals(newStart, result.getStart());
        verify(workoutRepo).save(workout);
    }

    @Test
    public void updateWorkout_shouldThrow_whenUserMismatch() {
        UserEntity workoutUser = new UserEntity();
        workoutUser.setUsername("john");

        Workout workout = new Workout();
        workout.setUserEntity(workoutUser);

        when(workoutRepo.findById(1L)).thenReturn(Optional.of(workout));
        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(new UserEntity()));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> workoutService.updateWorkout("ann", 1, new WorkoutUpdateDto()));

        assertTrue(ex.getMessage().contains("not matching"));
    }

    @Test
    public void deleteWorkout_shouldDelete() {
        UserEntity user = new UserEntity();
        user.setUsername("ann");

        Workout workout = new Workout();
        workout.setUserEntity(user);

        when(workoutRepo.findById(1L)).thenReturn(Optional.of(workout));
        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(user));

        workoutService.deleteWorkout(1, "ann");

        verify(workoutRepo).deleteById(1L);
    }

    @Test
    public void deleteWorkout_shouldThrow_whenMismatch() {
        UserEntity workoutUser = new UserEntity();
        workoutUser.setUsername("john");

        Workout workout = new Workout();
        workout.setUserEntity(workoutUser);

        when(workoutRepo.findById(1L)).thenReturn(Optional.of(workout));
        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(new UserEntity()));

        assertThrows(RuntimeException.class,
                () -> workoutService.deleteWorkout(1, "ann"));
    }
}
