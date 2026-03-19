package mappersTest;

import fitenessTrackerApp.dto.exercise.ExerciseCreateDto;
import fitenessTrackerApp.dto.exercise.ExerciseResponseDTO;
import fitenessTrackerApp.etities.Exercise;
import fitenessTrackerApp.etities.Workout;
import fitenessTrackerApp.mappers.ExerciseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExerciseMapperTest {
    private ExerciseMapper exerciseMapper;

    @BeforeEach
    public void setUp() {
        exerciseMapper = Mappers.getMapper(ExerciseMapper.class);
    }

    @Test
    public void testToExerciseResponseDTO() {
        Workout workout = new Workout();
        workout.setId(1);

        Exercise exercise = Exercise.builder()
                .id(1)
                .name("Pushup")
                .reps(15)
                .weightKg(0)
                .workout(workout)
                .build();

        ExerciseResponseDTO exerciseResponseDTO = exerciseMapper.toExerciseResponseDTO(exercise);
        assertNotNull(exerciseResponseDTO);
        assertEquals("Pushup", exerciseResponseDTO.getName());
        assertEquals(15, exerciseResponseDTO.getReps());
        assertEquals(0, exerciseResponseDTO.getWeightKg());
        assertEquals(1, exerciseResponseDTO.getWorkoutId());
    }

    @Test
    public void testFromExerciseCreateDto() {
        ExerciseCreateDto exerciseCreateDto = new ExerciseCreateDto();
        exerciseCreateDto.setName("Squat");
        exerciseCreateDto.setReps(20);
        exerciseCreateDto.setWeightKg(50);

        Exercise exercise = exerciseMapper.fromExerciseCreateDto(exerciseCreateDto);

        assertNotNull(exercise);
        assertEquals("Squat", exercise.getName());
        assertEquals(20, exercise.getReps());
        assertEquals(50, exercise.getWeightKg());
    }

}
