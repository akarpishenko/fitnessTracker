package fitenessTrackerApp.repository;

import fitenessTrackerApp.etities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepo extends JpaRepository<Workout, Long> {
    List<Workout> findAllByUserId(long userId);
}
