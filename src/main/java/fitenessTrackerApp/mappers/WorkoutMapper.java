package fitenessTrackerApp.mappers;

import fitenessTrackerApp.dto.workout.WorkoutCreateDto;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import fitenessTrackerApp.etities.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {UserMapper.class, ExerciseMapper.class})
public interface WorkoutMapper {
    Workout fromWorkoutCreateDto(WorkoutCreateDto workoutCreateDto);

    @Mapping(target = "userId", source = "user.id")
    WorkoutResponseDTO toWorkoutResponseDTO(Workout workout);
}
