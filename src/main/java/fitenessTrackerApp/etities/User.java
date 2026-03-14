package fitenessTrackerApp.etities;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String name;
    public String lastName;
    public String email;
    public String username;
    @OneToMany(mappedBy = "user")
    private List<Run> runs;

    @OneToMany(mappedBy = "user")
    private List<Workout> workouts;
}
