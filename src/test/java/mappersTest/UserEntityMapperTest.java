package mappersTest;

import fitenessTrackerApp.dto.user.UserCreateDto;
import fitenessTrackerApp.dto.user.UserResponseDTO;
import fitenessTrackerApp.etities.UserEntity;
import fitenessTrackerApp.etities.Workout;
import fitenessTrackerApp.mappers.UserMapper;
import fitenessTrackerApp.mappers.WorkoutMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserEntityMapperTest {
    private UserMapper userMapper;
    private WorkoutMapper workoutMapper;

    @BeforeEach
    public void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
        workoutMapper = Mappers.getMapper(WorkoutMapper.class);
    }

    @Test
    public void testToUserResponse() {
        LocalDateTime start = LocalDateTime.now().minusHours(10);
        LocalDateTime finish = LocalDateTime.now().minusHours(9);
        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .name("Anna")
                .lastName("Orange")
                .email("aor@gmail.com")
                .username("aor")
                .build();
        Workout workout = Workout.builder()
                .id(1)
                .start(start)
                .finish(finish)
                .userEntity(userEntity)
                .caloriesBurned(100)
                .build();
        userEntity.setWorkouts(List.of(workout));
        UserResponseDTO userResponseDTO = userMapper.toUserResponse(userEntity);
        assertNotNull(userResponseDTO);
        assertEquals("Anna", userResponseDTO.getName());
        assertEquals("Orange", userResponseDTO.getLastName());
        assertEquals("aor", userResponseDTO.getUsername());
        assertEquals(List.of(workoutMapper.toWorkoutResponseDTO(workout)), userResponseDTO.getWorkouts());
    }

    @Test
    public void testFromUserCreateDto() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .name("Anna")
                .lastName("Orange")
                .email("aor@gmail.com")
                .username("aor")
                .build();

        UserEntity userEntity = userMapper.fromUserCreateDto(userCreateDto);
        assertNotNull(userEntity);
        assertEquals("Anna", userEntity.getName());
        assertEquals("Orange", userEntity.getLastName());
        assertEquals("aor@gmail.com", userEntity.getEmail());
        assertEquals("aor", userEntity.getUsername());
    }
}
