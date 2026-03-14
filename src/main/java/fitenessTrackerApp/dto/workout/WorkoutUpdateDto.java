package fitenessTrackerApp.dto.workout;

import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkoutUpdateDto {
    @Past
    private LocalDateTime start;
    @Past
    private LocalDateTime finish;
}
