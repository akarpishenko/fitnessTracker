package mappersTest;

import fitenessTrackerApp.dto.run.RunCreateDto;
import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.etities.Run;
import fitenessTrackerApp.etities.User;
import fitenessTrackerApp.mappers.RunMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RunMapperTest {
    private final LocalDateTime start = LocalDateTime.now().minusHours(10);
    private final LocalDateTime finish = LocalDateTime.now().minusHours(9);
    private RunMapper runMapper;

    @BeforeEach
    public void setUp() {
        runMapper = Mappers.getMapper(RunMapper.class);
    }

    @Test
    public void testFromRunCreateDto() {

        RunCreateDto runCreateDto = RunCreateDto.builder()
                .start(start)
                .finish(finish)
                .distanceKm(12)
                .averagePace(11.5)
                .build();
        Run run = runMapper.fromRunCreateDto(runCreateDto);

        assertNotNull(run);
        assertEquals(start, run.getStart());
        assertEquals(finish, run.getFinish());
        assertEquals(12, run.getDistanceKm());
        assertEquals(11.5, run.getAveragePace());
    }

    @Test
    public void testToRunResponseDTO() {
        User user = new User();
        user.setId(1);
        Run run = Run.builder()
                .id(1)
                .user(user)
                .start(start)
                .finish(finish)
                .distanceKm(12)
                .averagePace(11.5)
                .caloriesBurned(120)
                .build();
        RunResponseDTO runResponseDTO = runMapper.toRunResponseDTO(run);
        assertNotNull(runResponseDTO);
        assertEquals(1, runResponseDTO.getUserId());
        assertEquals(start, runResponseDTO.getStart());
        assertEquals(finish, runResponseDTO.getFinish());
        assertEquals(12, runResponseDTO.getDistanceKm());
        assertEquals(11.5, runResponseDTO.getAveragePace());
        assertEquals(120, runResponseDTO.getCaloriesBurned());
    }
}
