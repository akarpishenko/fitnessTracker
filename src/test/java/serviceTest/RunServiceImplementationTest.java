package serviceTest;

import fitenessTrackerApp.dto.run.RunCreateDto;
import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.dto.run.RunUpdateDto;
import fitenessTrackerApp.etities.Run;
import fitenessTrackerApp.etities.User;
import fitenessTrackerApp.mappers.RunMapper;
import fitenessTrackerApp.repository.RunRepo;
import fitenessTrackerApp.repository.UserRepo;
import fitenessTrackerApp.service.RunServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RunServiceImplementationTest {

    private final LocalDateTime start = LocalDateTime.now().minusHours(5);
    private final LocalDateTime finish = LocalDateTime.now().minusHours(4);

    @Mock
    private RunRepo runRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private RunMapper runMapper;

    @InjectMocks
    private RunServiceImplementation runServiceImplementation;

    @Test
    public void createRun_shouldReturnRunDto() {

        User user = new User();
        user.setId(1);

        Run run = Run.builder().start(start).finish(finish).distanceKm(5).build();

        RunCreateDto createDto = RunCreateDto.builder().start(start).finish(finish).distanceKm(5).build();

        RunResponseDTO responseDTO = RunResponseDTO.builder().start(start).finish(finish).distanceKm(5).caloriesBurned(310).build();

        when(runMapper.fromRunCreateDto(Mockito.any())).thenReturn(run);
        when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));
        when(runRepo.save(Mockito.any())).thenReturn(run);
        when(runMapper.toRunResponseDTO(Mockito.any())).thenReturn(responseDTO);

        RunResponseDTO savedRun = runServiceImplementation.createRun(1, createDto);

        assertNotNull(savedRun);
        assertEquals(start, savedRun.getStart());
        assertEquals(finish, savedRun.getFinish());

        verify(runRepo, times(1)).save(run);
    }

    @Test
    public void getRunById_shouldReturnRun() {

        Run run = Run.builder()
                .start(start)
                .finish(finish)
                .build();

        RunResponseDTO dto = RunResponseDTO.builder()
                .start(start)
                .finish(finish)
                .build();

        when(runRepo.findById(Mockito.any())).thenReturn(Optional.of(run));
        when(runMapper.toRunResponseDTO(Mockito.any())).thenReturn(dto);

        RunResponseDTO returned = runServiceImplementation.getRunById(1L);

        assertNotNull(returned);
        assertEquals(start, returned.getStart());
        assertEquals(finish, returned.getFinish());

        verify(runRepo, times(1)).findById(1L);
    }

    @Test
    public void getRunsByUser_shouldReturnRuns() {

        Run run = Run.builder()
                .start(start)
                .finish(finish)
                .build();

        RunResponseDTO dto = RunResponseDTO.builder()
                .start(start)
                .finish(finish)
                .build();

        when(runRepo.findAllByUserId(1)).thenReturn(List.of(run));

        when(runMapper.toRunResponseDTOList(Mockito.any())).thenReturn(List.of(dto));

        List<RunResponseDTO> runs = runServiceImplementation.getRunsByUser(1);

        assertNotNull(runs);
        assertEquals(1, runs.size());

        verify(runRepo, times(1)).findAllByUserId(1L);
    }

    @Test
    public void updateRun_shouldUpdateRun() {

        User user = new User();
        user.setId(1);

        Run run = Run.builder().user(user).start(start).finish(finish).distanceKm(5).averagePace(5.5).build();

        LocalDateTime newStart = start.minusHours(1);

        RunUpdateDto updateDto = new RunUpdateDto();
        updateDto.setStart(newStart);
        updateDto.setDistanceKm(10.0);

        RunResponseDTO responseDTO = RunResponseDTO.builder().start(newStart).distanceKm(10).build();

        when(runRepo.findById(Mockito.any())).thenReturn(Optional.of(run));
        when(runRepo.save(Mockito.any())).thenReturn(run);
        when(runMapper.toRunResponseDTO(Mockito.any())).thenReturn(responseDTO);

        RunResponseDTO updated = runServiceImplementation.updateRun(1, 1, updateDto);

        assertEquals(newStart, updated.getStart());

        verify(runRepo).save(run);
    }

    @Test
    public void deleteRun_shouldDeleteRun() {

        User user = new User();
        user.setId(1);

        Run run = Run.builder().user(user).build();

        when(runRepo.findById(Mockito.any())).thenReturn(Optional.of(run));

        runServiceImplementation.deleteRun(1, 1);

        verify(runRepo, times(1)).deleteById(1L);
    }

    @Test
    public void createRun_shouldThrowException_whenUserNotFound() {

        RunCreateDto dto = RunCreateDto.builder()
                .start(start)
                .finish(finish)
                .distanceKm(5)
                .build();

        Run run = Run.builder()
                .start(start)
                .finish(finish)
                .distanceKm(5)
                .build();

        when(runMapper.fromRunCreateDto(Mockito.any())).thenReturn(run);
        when(userRepo.findById(Mockito.any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> runServiceImplementation.createRun(1, dto)
        );

        assertEquals("User not found with id 1", exception.getMessage());
    }

    @Test
    public void getRunById_shouldThrowException_whenRunNotFound() {

        when(runRepo.findById(Mockito.any())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> runServiceImplementation.getRunById(1)
        );

        assertEquals("Run not found with id 1", exception.getMessage());
    }

    @Test
    public void updateRun_shouldThrowException_whenUserMismatch() {

        User runUser = new User();
        runUser.setId(2);

        Run run = Run.builder()
                .user(runUser)
                .start(start)
                .finish(finish)
                .distanceKm(5)
                .build();

        RunUpdateDto updateDto = new RunUpdateDto();

        when(runRepo.findById(Mockito.any())).thenReturn(Optional.of(run));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> runServiceImplementation.updateRun(1, 1, updateDto)
        );

        assertTrue(exception.getMessage().contains("is not matching"));
    }

    @Test
    public void deleteRun_shouldThrowException_whenUserMismatch() {

        User runUser = new User();
        runUser.setId(2);

        Run run = Run.builder()
                .user(runUser)
                .build();

        when(runRepo.findById(Mockito.any())).thenReturn(Optional.of(run));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> runServiceImplementation.deleteRun(1, 1)
        );

        assertTrue(exception.getMessage().contains("is not matching"));
    }
}
