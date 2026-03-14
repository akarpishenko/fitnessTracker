package fitenessTrackerApp.dto.user;

import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    private String name;
    private String lastName;
    private String username;
    private List<RunResponseDTO> runs;
    private List<WorkoutResponseDTO> workouts;
}
