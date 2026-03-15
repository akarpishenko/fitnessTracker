package fitenessTrackerApp.dto.workout;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkoutCreateDto {
    @NotNull
    @Past
    private LocalDateTime start;
    @NotNull
    @Past
    private LocalDateTime finish;
}
