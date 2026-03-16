package fitenessTrackerApp.dto.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseResponseDTO {
    private long id;
    private String name;
    private int reps;
    private double weightKg;
    private long workoutId;
}
