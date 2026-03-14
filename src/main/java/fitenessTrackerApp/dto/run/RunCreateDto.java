package fitenessTrackerApp.dto.run;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RunCreateDto {
    @NotNull
    @Past
    private LocalDateTime start;
    @Past
    @NotNull
    private LocalDateTime finish;
    private double distanceKm;
    private double averagePace;
}
