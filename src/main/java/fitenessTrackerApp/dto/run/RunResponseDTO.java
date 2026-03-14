package fitenessTrackerApp.dto.run;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RunResponseDTO {
    private int userId;
    private LocalDateTime start;
    private LocalDateTime finish;
    private double distanceKm;
    private double averagePace;
    private double caloriesBurned;
}
