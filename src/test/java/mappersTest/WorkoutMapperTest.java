package mappersTest;

import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.etities.Workout;
import fitenessTrackerApp.mappers.WorkoutMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WorkoutMapperTest {
    private final LocalDateTime start = LocalDateTime.now().minusHours(10);
    private final LocalDateTime finish = LocalDateTime.now().minusHours(9);
    private WorkoutMapper workoutMapper;

    @BeforeEach
    public void setUp() {
        workoutMapper = Mappers.getMapper(WorkoutMapper.class);
    }

    @Test
    public void testFromWorkoutCreateDto() {
        WorkoutCreateDto workoutCreateDto = new WorkoutCreateDto();
        workoutCreateDto.setStart(start);
        workoutCreateDto.setFinish(finish);
        Workout workout = workoutMapper.fromWorkoutCreateDto(workoutCreateDto);
        assertNotNull(workout);
        assertEquals(start, workout.getStart());
        assertEquals(finish, workout.getFinish());
    }
}
