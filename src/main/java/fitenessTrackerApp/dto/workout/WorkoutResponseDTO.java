package fitenessTrackerApp.dto.workout;

import fitenessTrackerApp.etities.Exercise;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkoutResponseDTO {
    private int id;
    private Long userId;
    private LocalDateTime start;
    private LocalDateTime finish;
    private double caloriesBurned;
    private List<Exercise> exercises;
}
