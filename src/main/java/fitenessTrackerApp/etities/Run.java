package fitenessTrackerApp.etities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Run {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @ManyToOne
    private User user;
    private LocalDateTime start;
    private LocalDateTime finish;
    private double distanceKm;
    private double averagePace;
    private double caloriesBurned;
}
