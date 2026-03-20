package serviceTest;

import fitenessTrackerApp.dto.run.RunCreateDto;
import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.dto.run.RunUpdateDto;
import fitenessTrackerApp.etities.Run;
import fitenessTrackerApp.etities.UserEntity;
import fitenessTrackerApp.exception.RunNotFoundException;
import fitenessTrackerApp.exception.UserNotFoundException;
import fitenessTrackerApp.mappers.RunMapper;
import fitenessTrackerApp.repository.RunRepo;
import fitenessTrackerApp.repository.UserRepo;
import fitenessTrackerApp.service.RunServiceImplementation;
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
    private RunServiceImplementation runService;

    @Test
    public void createRun_shouldReturnRunDto() {
        UserEntity user = new UserEntity();
        user.setUsername("ann");

        Run run = Run.builder()
                .start(start)
                .finish(finish)
                .distanceKm(5)
                .build();

        RunCreateDto dto = RunCreateDto.builder()
                .start(start)
                .finish(finish)
                .distanceKm(5)
                .build();

        RunResponseDTO response = RunResponseDTO.builder()
                .start(start)
                .finish(finish)
                .distanceKm(5)
                .build();

        when(runMapper.fromRunCreateDto(dto)).thenReturn(run);
        when(userRepo.findByUsername("ann")).thenReturn(Optional.of(user));
        when(runRepo.save(run)).thenReturn(run);
        when(runMapper.toRunResponseDTO(run)).thenReturn(response);

        RunResponseDTO result = runService.createRun("ann", dto);

        assertNotNull(result);
        assertEquals(start, result.getStart());
        verify(runRepo).save(run);
    }

    @Test
    public void createRun_shouldThrow_whenUserNotFound() {
        RunCreateDto dto = new RunCreateDto();

        when(userRepo.findByUsername("omelet")).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> runService.createRun("omelet", dto));

        assertTrue(ex.getMessage().contains("omelet"));
    }

    @Test
    public void getRunById_shouldReturnRun() {
        Run run = Run.builder().start(start).finish(finish).build();
        RunResponseDTO dto = RunResponseDTO.builder().start(start).finish(finish).build();

        when(runRepo.findById(1L)).thenReturn(Optional.of(run));
        when(runMapper.toRunResponseDTO(run)).thenReturn(dto);

        RunResponseDTO result = runService.getRunById(1);

        assertEquals(start, result.getStart());
        verify(runRepo).findById(1L);
    }

    @Test
    public void getRunById_shouldThrow_whenNotFound() {
        when(runRepo.findById(1L)).thenReturn(Optional.empty());

        RunNotFoundException ex = assertThrows(RunNotFoundException.class,
                () -> runService.getRunById(1));

        assertTrue(ex.getMessage().contains("1"));
    }

    @Test
    public void getRunsByUser_shouldReturnRuns() {
        Run run = new Run();
        RunResponseDTO dto = new RunResponseDTO();

        when(runRepo.findAllByUserEntityUsername("ann"))
                .thenReturn(List.of(run));

        when(runMapper.toRunResponseDTOList(List.of(run)))
                .thenReturn(List.of(dto));

        List<RunResponseDTO> result = runService.getRunsByUser("ann");

        assertEquals(1, result.size());
        verify(runRepo).findAllByUserEntityUsername("ann");
    }

    @Test
    public void updateRun_shouldUpdateRun() {
        UserEntity user = new UserEntity();
        user.setUsername("ann");

        Run run = Run.builder()
                .userEntity(user)
                .start(start)
                .finish(finish)
                .distanceKm(5)
                .build();

        LocalDateTime newStart = start.minusHours(1);

        RunUpdateDto dto = new RunUpdateDto();
        dto.setStart(newStart);
        dto.setDistanceKm(10.0);

        RunResponseDTO response = new RunResponseDTO();
        response.setStart(newStart);

        when(runRepo.findById(1L)).thenReturn(Optional.of(run));
        when(runRepo.save(run)).thenReturn(run);
        when(runMapper.toRunResponseDTO(run)).thenReturn(response);

        RunResponseDTO result = runService.updateRun("ann", 1, dto);

        assertEquals(newStart, result.getStart());
        verify(runRepo).save(run);
    }

    @Test
    public void updateRun_shouldThrow_whenUserMismatch() {
        UserEntity otherUser = new UserEntity();
        otherUser.setUsername("john");

        Run run = new Run();
        run.setUserEntity(otherUser);

        when(runRepo.findById(1L)).thenReturn(Optional.of(run));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> runService.updateRun("ann", 1, new RunUpdateDto()));

        assertTrue(ex.getMessage().contains("not matching"));
    }

    @Test
    public void deleteRun_shouldDeleteRun() {
        UserEntity user = new UserEntity();
        user.setUsername("ann");

        Run run = new Run();
        run.setUserEntity(user);

        when(runRepo.findById(1L)).thenReturn(Optional.of(run));

        runService.deleteRun(1, "ann");

        verify(runRepo).deleteById(1L);
    }

    @Test
    public void deleteRun_shouldThrow_whenUserMismatch() {
        UserEntity otherUser = new UserEntity();
        otherUser.setUsername("john");

        Run run = new Run();
        run.setUserEntity(otherUser);

        when(runRepo.findById(1L)).thenReturn(Optional.of(run));

        assertThrows(RuntimeException.class,
                () -> runService.deleteRun(1, "ann"));
    }
}
