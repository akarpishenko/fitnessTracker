package fitenessTrackerApp.dto.exercise;

import lombok.Data;

@Data
public class ExerciseResponseDTO {
    private int id;
    private String name;
    private int reps;
    private double weightKg;
    private int workoutId;
}
