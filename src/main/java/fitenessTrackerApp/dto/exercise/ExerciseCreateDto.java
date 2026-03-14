package fitenessTrackerApp.dto.exercise;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class ExerciseCreateDto {
    @NotBlank
    private String name;
    private int reps;
    private double weightKg;
    private long workoutId;
}
