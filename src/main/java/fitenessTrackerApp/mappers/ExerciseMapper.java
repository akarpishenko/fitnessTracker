package fitenessTrackerApp.mappers;

import fitenessTrackerApp.dto.exercise.ExerciseCreateDto;
import fitenessTrackerApp.dto.exercise.ExerciseResponseDTO;
import fitenessTrackerApp.etities.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {WorkoutMapper.class})
public interface ExerciseMapper {
    @Mapping(target = "workoutId", source = "workout.id")
    ExerciseResponseDTO toExerciseResponseDTO(Exercise exercise);

    Exercise fromExerciseCreateDto(ExerciseCreateDto exerciseCreateDto);

    List<ExerciseResponseDTO> toExerciseResponseDTOList(List<Exercise> exercises);
}
