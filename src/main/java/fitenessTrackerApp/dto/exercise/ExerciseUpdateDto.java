package fitenessTrackerApp.dto.exercise;

import lombok.Data;

@Data
public class ExerciseUpdateDto {
    private String name;
    private Integer reps;
    private Double weightKg;
    private Long workoutId;
}
