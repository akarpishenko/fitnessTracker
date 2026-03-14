package fitenessTrackerApp.repository;

import fitenessTrackerApp.etities.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunRepo extends JpaRepository<Run, Long> {
}
