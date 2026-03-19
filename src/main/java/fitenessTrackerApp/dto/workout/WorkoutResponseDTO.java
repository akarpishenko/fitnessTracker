package fitenessTrackerApp.dto.workout;

import fitenessTrackerApp.dto.exercise.ExerciseResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutResponseDTO {
    private long id;
    private long userId;
    private LocalDateTime start;
    private LocalDateTime finish;
    private double caloriesBurned;
    private List<ExerciseResponseDTO> exercises;
}
