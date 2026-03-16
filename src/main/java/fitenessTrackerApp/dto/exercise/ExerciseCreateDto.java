package fitenessTrackerApp.dto.exercise;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseCreateDto {
    @NotBlank
    private String name;
    @Positive
    private int reps;
    @Min(value = 0L)
    private double weightKg;
    private long workoutId;
}
