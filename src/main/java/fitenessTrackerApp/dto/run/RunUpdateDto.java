package fitenessTrackerApp.dto.run;

import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunUpdateDto {
    @Past
    private LocalDateTime start;
    @Past
    private LocalDateTime finish;
    private Double distanceKm;
    private Double averagePace;
}
