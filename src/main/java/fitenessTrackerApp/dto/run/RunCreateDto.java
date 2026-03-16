package fitenessTrackerApp.dto.run;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class RunCreateDto {
    @NotNull
    @Past
    private LocalDateTime start;
    @Past
    @NotNull
    private LocalDateTime finish;
    @Positive
    private double distanceKm;
    @Positive
    private double averagePace;
}
