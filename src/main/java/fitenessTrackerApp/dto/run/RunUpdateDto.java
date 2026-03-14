package fitenessTrackerApp.dto.run;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RunUpdateDto {
    private LocalDateTime start;
    private LocalDateTime finish;
    private Double distanceKm;
    private Double averagePace;
}
