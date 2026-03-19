package fitenessTrackerApp.repository;

import fitenessTrackerApp.etities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepo extends JpaRepository<Exercise, Long> {
    List<Exercise> findAllByWorkoutId(long workoutId);
}
