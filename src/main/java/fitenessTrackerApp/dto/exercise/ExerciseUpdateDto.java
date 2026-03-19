package fitenessTrackerApp.dto.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseUpdateDto {
    private String name;
    private Integer reps;
    private Double weightKg;
}
