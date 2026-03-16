package fitenessTrackerApp.dto.run;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunResponseDTO {
    private long userId;
    private LocalDateTime start;
    private LocalDateTime finish;
    private double distanceKm;
    private double averagePace;
    private double caloriesBurned;
}
