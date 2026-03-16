package fitenessTrackerApp.dto.user;

import fitenessTrackerApp.dto.run.RunResponseDTO;
import fitenessTrackerApp.dto.workout.WorkoutResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private long id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private List<RunResponseDTO> runs;
    private List<WorkoutResponseDTO> workouts;
}
